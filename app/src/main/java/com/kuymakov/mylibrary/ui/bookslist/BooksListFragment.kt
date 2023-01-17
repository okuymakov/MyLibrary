package com.kuymakov.mylibrary.ui.bookslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.base.extensions.actionBar
import com.kuymakov.mylibrary.base.extensions.launchOnLifecycle
import com.kuymakov.mylibrary.base.extensions.showBackButton
import com.kuymakov.mylibrary.databinding.FragmentBooksListBinding
import com.kuymakov.mylibrary.models.Response
import com.kuymakov.mylibrary.ui.bookslist.adapter.BooksAdapter
import com.google.android.material.snackbar.Snackbar
import com.skydoves.androidveil.VeilRecyclerFrameView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class BooksListFragment : BaseFragment<FragmentBooksListBinding>() {
    private val args by navArgs<BooksListFragmentArgs>()
    private val viewModel: BooksListViewModel by viewModels()
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBooksListBinding
        get() = FragmentBooksListBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchBooks(query = "subject:${args.category}")
        showBackButton(true)
        actionBar?.title = args.categoryName
        bind()
        val (list, adapter) = setupList()
        setupListSideEffects(list, adapter, view)
    }

    private fun setupList(): Pair<VeilRecyclerFrameView, BooksAdapter> {
        val list = binding.booksList
        val adapter = BooksAdapter()
        list.run {
            setVeilLayout(R.layout.books_item)
            setAdapter(adapter)
            setLayoutManager(GridLayoutManager(context, 2))
            addVeiledItems(10)
        }
        return Pair(list, adapter)
    }

    private fun setupListSideEffects(
        list: VeilRecyclerFrameView,
        adapter: BooksAdapter,
        view: View,
    ) {
        launchOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.books.collect { value ->
                when (value) {
                    is Response.Loading -> {
                        list.veil()
                        binding.swipeRefresh.isRefreshing = true
                    }
                    is Response.Success -> {
                        list.unVeil()
                        binding.swipeRefresh.isRefreshing = false
                        adapter.addBooks(value.el.books)
                        Timber.d(value.el.books.size.toString())
                    }
                    is Response.Error -> {
                        list.unVeil()
                        binding.swipeRefresh.isRefreshing = false
                        Snackbar.make(
                            view,
                            "Произошла ошибка",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun bind() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.fetchBooks(query = "subject:${args.category}")
        }
    }

}