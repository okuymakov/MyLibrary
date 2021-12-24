package com.example.mylibrary.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mylibrary.R
import com.example.mylibrary.base.BaseFragment
import com.example.mylibrary.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding
        get() = FragmentMainBinding::inflate
//    val navController by lazy {
//        //binding.navHostFragment.getFragment<NavHostFragment>().navController
//        val navHostFragment =
//            childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navHostFragment.navController
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        requireActivity().onBackPressedDispatcher.addCallback(
//            this,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    //childFragmentManager.popBackStack()
//                }
//            })
//        val bottomNav = binding.bottomNavigation
//
//        bottomNav.setupWithNavController(navController)
//
//        childFragmentManager.addOnBackStackChangedListener {
//            Timber.d(childFragmentManager.backStackEntryCount.toString())
//        }
//    }
}

