package com.kuymakov.mylibrary.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.kuymakov.mylibrary.R
import com.kuymakov.mylibrary.base.BaseFragment
import com.kuymakov.mylibrary.databinding.FragmentMainBinding


class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate
    private val navController by lazy {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navHostFragment.navController
    }

    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.nav_catalog, R.id.nav_my_books)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupBottomNav()
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolBar = binding.topAppBar
        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(toolBar)
            setupActionBarWithNavController(
                navController,
                appBarConfiguration
            )
        }
        toolBar.showOverflowMenu()
    }

    private fun setupBottomNav() {
        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
        bottomNav.setOnItemReselectedListener {
            val id = navController.currentDestination?.parent?.startDestinationId
            if (id != null) {
                navController.popBackStack(id, inclusive = false)
            }
        }
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_app_bar, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.search -> {
                        findNavController().navigate(MainFragmentDirections.actionMainFragmentToFragmentSearch())
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun hideBottomNav() {
        binding.bottomNavigation.visibility = View.GONE
    }

    fun showBottomNav() {
        binding.bottomNavigation.visibility = View.VISIBLE
    }
}

