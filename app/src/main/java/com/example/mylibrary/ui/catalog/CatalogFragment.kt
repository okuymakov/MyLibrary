package com.example.mylibrary.ui.catalog

import android.annotation.SuppressLint
import android.graphics.Insets.add
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.mylibrary.base.BaseFragment
import com.example.mylibrary.databinding.FragmentCatalogBinding
import com.example.mylibrary.models.BooksCategory
import com.example.mylibrary.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CatalogFragment : BaseFragment<FragmentCatalogBinding>() {
    private val viewModel: CatalogViewModel by viewModels()
    private val navController : NavController by lazy {
        findNavController()
    }

    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCatalogBinding
        get() = FragmentCatalogBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val catalog = binding.catalog
        catalog.adapter = CatalogAdapter(::onCatalogClick)
    }

    private fun onCatalogClick(booksCategory: BooksCategory) {
        val res = booksCategory.name
        navController.navigate(CatalogFragmentDirections.actionCatalogFragmentToBooksListFragment(res))
    }

}