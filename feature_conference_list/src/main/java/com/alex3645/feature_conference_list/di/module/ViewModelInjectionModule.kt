package com.alex3645.feature_conference_list.di.module

import androidx.lifecycle.ViewModelProvider
import com.alex3645.feature_conference_list.di.util.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelInjectionModule {
    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory
}