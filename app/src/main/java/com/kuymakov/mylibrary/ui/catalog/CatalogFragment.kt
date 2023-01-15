package com.kuymakov.mylibrary.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.databinding.FragmentCatalogBinding
import com.kuymakov.mylibrary.models.BooksCategory
import com.kuymakov.mylibrary.ui.catalog.adapter.CatalogAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : BaseFragment<FragmentCatalogBinding>() {
    private val navController: NavController by lazy {
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
        navController.navigate(
            CatalogFragmentDirections.actionCatalogFragmentToBooksListFragment(
                booksCategory.displayName,
                booksCategory.name
            )
        )
    }
}