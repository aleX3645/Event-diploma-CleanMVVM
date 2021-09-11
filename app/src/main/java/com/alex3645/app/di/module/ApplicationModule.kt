package com.alex3645.app.di.module

import android.app.Application
import android.content.Context
import com.alex3645.app.App
import com.alex3645.app.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApplication(): App {
        return app
    }
}
