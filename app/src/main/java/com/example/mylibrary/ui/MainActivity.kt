package com.example.mylibrary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mylibrary.R
import com.example.mylibrary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.navController
    }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(R.id.nav_catalog, R.id.nav_my_books)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.bottomNavigation

        val bottomNav = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)

        val appBar = binding.topAppBar
        setSupportActionBar(appBar)
        appBar.showOverflowMenu()
        setupActionBarWithNavController(navController,appBarConfiguration)

        appBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search -> {

                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val searchItem = appBar.menu.findItem(R.id.search)
            val searchView: SearchView = searchItem.actionView as SearchView
            if (destination.id != R.id.searchFragment) {
                searchView.isIconified = true
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar, menu)
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnSearchClickListener {
            if (navController.currentDestination?.id != R.id.searchFragment) {
                navController.navigate(R.id.searchFragment)
            }
        }

        return true
    }


}

