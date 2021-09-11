package com.alex3645.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.alex3645.app.di.component.ApplicationComponent
import com.alex3645.app.di.component.DaggerApplicationComponent
//import com.alex3645.app.di.component.ApplicationComponent
//import com.alex3645.app.di.component.DaggerApplicationComponent
import com.alex3645.app.di.module.ApplicationModule
import com.alex3645.eventdiploma_mvvm.BuildConfig
import com.google.android.play.core.splitcompat.SplitCompatApplication
import javax.inject.Inject

class App: Application() {

    @Inject lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setup()

        if (BuildConfig.DEBUG) {
            // Maybe TimberPlant etc.
        }
    }

    private fun setup() {
        DaggerApplicationComponent.factory().create(ApplicationModule(this)).inject(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }
}