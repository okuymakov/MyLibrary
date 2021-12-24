package com.example.mylibrary.ui.search

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.mylibrary.R
import com.example.mylibrary.base.BaseFragment
import com.example.mylibrary.base.extensions.getQueryText
import com.example.mylibrary.databinding.FragmentSearchBinding
import com.example.mylibrary.models.Response
import com.example.mylibrary.ui.MainActivity
import com.example.mylibrary.ui.bookslist.BooksAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = binding.booksListFragment.booksList
        val adapter = BooksAdapter()
        list.adapter = adapter

        val activity = requireActivity() as MainActivity
        activity.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.books.collect { value ->
                    when (value) {
                        is Response.Loading -> {
                        }
                        is Response.Success -> {
                            Timber.d(value.el.books.size.toString())
                            adapter.updateBooks(value.el.books)
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

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.isIconified = false
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchView.getQueryText().debounce(300)
                    .filter { query ->
                        query.isNotBlank()
                    }
                    .distinctUntilChanged()
                    .flowOn(Dispatchers.Default)
                    .collect { result ->
                        viewModel.fetchBooks(result)
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