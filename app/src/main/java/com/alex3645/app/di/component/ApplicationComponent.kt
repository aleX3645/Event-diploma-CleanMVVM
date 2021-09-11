package com.alex3645.app.di.component

import android.content.Context
import com.alex3645.app.App
import com.alex3645.app.di.module.ApplicationModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(applicationModule: ApplicationModule): ApplicationComponent
    }

    fun inject(application: App)
}