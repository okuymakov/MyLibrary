package com.kuymakov.mylibrary.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.base.extensions.getQueryText
import com.kuymakov.mylibrary.base.extensions.launchOnLifecycle
import com.kuymakov.mylibrary.base.extensions.showBackButton
import com.kuymakov.mylibrary.base.extensions.showKeyboard
import com.kuymakov.mylibrary.databinding.FragmentSearchBinding
import com.kuymakov.mylibrary.models.Response
import com.kuymakov.mylibrary.ui.bookslist.adapter.BooksAdapter
import com.skydoves.androidveil.VeilRecyclerFrameView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber


@FlowPreview
@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding
        get() = FragmentSearchBinding::inflate
    private val viewModel by viewModels<SearchViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchData = savedInstanceState?.getString("searchData")
        binding.search.setQuery(searchData,false)
        viewModel.fetchBooks(binding.search.query.toString())
        setupToolbar()
        showBackButton(true)
        setupSearch()
        bind()
        val (list, adapter) = setupList()
        setupListSideEffects(list, adapter, view)
    }

    private fun setupToolbar() {
        val toolBar = binding.topAppBar
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(toolBar)
        }
        toolBar.showOverflowMenu()
    }

    private fun setupList(): Pair<VeilRecyclerFrameView, BooksAdapter> {
        val list = binding.booksListFragment.booksList
        val adapter = BooksAdapter()
        list.run {
            setVeilLayout(R.layout.books_item)
            setAdapter(adapter)
            setLayoutManager(GridLayoutManager(context, 2))
            addVeiledItems(10)
            unVeil()
        }
        return Pair(list, adapter)
    }

    private fun setupListSideEffects(
        list: VeilRecyclerFrameView,
        adapter: BooksAdapter,
        view: View,
    ) {
        val swipeRefresh = binding.booksListFragment.swipeRefresh
        launchOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.books.collect { value ->
                when (value) {
                    is Response.Loading -> {
                        list.veil()
                        swipeRefresh.isRefreshing = true
                    }
                    is Response.Success -> {
                        list.unVeil()
                        swipeRefresh.isRefreshing = false
                        adapter.updateBooks(value.el.books)
                        Timber.d(value.el.books.size.toString())
                    }
                    is Response.Error -> {
                        list.unVeil()
                        swipeRefresh.isRefreshing = false
                        Snackbar.make(
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

    private fun bind() {
        binding.booksListFragment.swipeRefresh.setOnRefreshListener {
            viewModel.fetchBooks(binding.search.query.toString())
        }
    }


    private fun setupSearch() {
        val searchView = binding.search
        searchView.requestFocus()
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showKeyboard()
            }
        }
        launchOnLifecycle(Lifecycle.State.STARTED) {
            searchView.getQueryText().debounce(300)
                .distinctUntilChanged()
                .flowOn(Dispatchers.Default)
                .collect { result ->
                    viewModel.fetchBooks(result)
                }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("searchData", binding.search.query.toString())
    }

}