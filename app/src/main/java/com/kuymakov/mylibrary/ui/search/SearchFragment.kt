package com.kuymakov.mylibrary.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.base.extensions.getQueryText
import com.kuymakov.mylibrary.base.extensions.hideKeyboard
import com.kuymakov.mylibrary.base.extensions.launchOnLifecycle
import com.kuymakov.mylibrary.base.extensions.showBackButton
import com.kuymakov.mylibrary.databinding.FragmentSearchBinding
import com.kuymakov.mylibrary.models.Response
import com.kuymakov.mylibrary.ui.bookslist.adapter.BooksAdapter
import com.kuymakov.mylibrary.ui.main.MainFragment
import com.skydoves.androidveil.VeilRecyclerFrameView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
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
        (requireParentFragment().requireParentFragment() as MainFragment).hideBottomNav()
        showBackButton(true)
        setupMenu()
        bind()
        val (list, adapter) = setupList()
        setupListSideEffects(list, adapter, view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireParentFragment().requireParentFragment() as MainFragment).showBottomNav()
        hideKeyboard()
    }

    private fun setupList(): Pair<VeilRecyclerFrameView, BooksAdapter> {
        val list = binding.booksListFragment.booksList
        val adapter = BooksAdapter()
        list.run {
            setVeilLayout(R.layout.books_item)
            setAdapter(adapter)
            setLayoutManager(GridLayoutManager(context, 2))
            addVeiledItems(15)
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
            viewModel.fetchBooks()
        }
    }


    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                val searchItem = menu.findItem(R.id.search)
                val searchView = searchItem.actionView as SearchView
                searchView.isIconified = false
                launchOnLifecycle(Lifecycle.State.STARTED) {
                    searchView.getQueryText().debounce(300)
                        .filter { query ->
                            query.isNotBlank()
                        }
                        .distinctUntilChanged()
                        .flowOn(Dispatchers.Default)
                        .collect { result ->
                            viewModel.searchData.value = result
                            viewModel.fetchBooks()
                        }
                }
                launchOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.searchData.collect { value ->
                        searchView.setQuery(value, false)
                    }
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = false
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}