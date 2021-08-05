package com.alex3645.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alex3645.eventdiploma_mvvm.R
import com.alex3645.eventdiploma_mvvm.databinding.ActivityNavHostBinding

class NavHostActivity : AppCompatActivity() {

    private val navController get() = findNavController(R.id.navHostFragment)
    private lateinit var binding: ActivityNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
    }
}