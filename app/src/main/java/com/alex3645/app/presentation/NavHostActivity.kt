package com.alex3645.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alex3645.eventdiploma_mvvm.R
import com.alex3645.eventdiploma_mvvm.databinding.ActivityNavHostBinding
import com.google.android.libraries.places.api.Places
import java.util.*

class NavHostActivity : AppCompatActivity() {

    private val navController get() = findNavController(R.id.navHostFragment)
    private lateinit var binding: ActivityNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.nav_graph)

        initUpperNavigation()
        initBottomNavigation()
    }

    private fun initUpperNavigation() {
        setSupportActionBar(binding.topNavigationAppBar)
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
    }
}