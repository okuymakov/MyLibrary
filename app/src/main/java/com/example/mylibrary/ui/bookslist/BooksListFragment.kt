package com.example.mylibrary.ui.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mylibrary.R
import com.example.mylibrary.base.BaseFragment
import com.example.mylibrary.base.extensions.getQueryText
import com.example.mylibrary.databinding.FragmentBooksListBinding
import com.example.mylibrary.models.Response
import com.example.mylibrary.ui.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import timber.log.Timber

@AndroidEntryPoint
class BooksListFragment : BaseFragment<FragmentBooksListBinding>() {
    private val viewModel: BooksListViewModel by viewModels()
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBooksListBinding
        get() = FragmentBooksListBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var result = "subject:"
        result += requireArguments().getString("category")

        viewModel.fetchBooks(query = result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as MainActivity

        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val list = binding.booksList
        val adapter = BooksAdapter()
        list.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.books.collect { value ->
                when (value) {
                    is Response.Loading -> {
                    }
                    is Response.Success -> {
                        Timber.d(value.el.books.size.toString())
                        adapter.addBooks(value.el.books)
                    }
                    is Response.Error -> Snackbar.make(
                        view,
                        "Произошла ошибка",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }



}