package com.alex3645.app.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.eventdiploma_mvvm.R
import com.alex3645.eventdiploma_mvvm.databinding.ActivityNavHostBinding
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit

import android.content.Intent
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


class NavHostActivity : AppCompatActivity() {

    private val navController get() = findNavController(R.id.navHostFragment)
    private lateinit var binding: ActivityNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        manageAccount()

        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.setGraph(R.navigation.nav_graph)

        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
    }

    public fun navigateToItemBottomNavigation(id: Int){
        navController.navigate(R.id.ConferenceListFeatureGraph)
    }

    private fun manageAccount(){
        val spManager = SharedPreferencesManager(context = applicationContext)

        if(!spManager.fetchRememberFlag()){
            spManager.removeUserData()
        }
    }
}