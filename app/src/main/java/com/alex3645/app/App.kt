package com.alex3645.app

import android.app.Application
import com.alex3645.app.di.component.ApplicationComponent
import com.alex3645.app.di.component.DaggerApplicationComponent
import com.alex3645.app.di.module.ApplicationModule
import javax.inject.Inject

class App: Application() {

    @Inject lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        setup()

    }

    private fun setup() {
        DaggerApplicationComponent.factory().create(ApplicationModule(this)).inject(this)
    }
}