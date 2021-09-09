package com.alex3645.app.di.module

import android.app.Application
import android.content.Context
import com.alex3645.app.App
import com.alex3645.app.di.scope.PerApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule{
}