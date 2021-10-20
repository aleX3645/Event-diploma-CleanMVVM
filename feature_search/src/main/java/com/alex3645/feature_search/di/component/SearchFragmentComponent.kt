package com.alex3645.feature_search.di.component

import com.alex3645.feature_search.di.module.SearchFragmentModule
import com.alex3645.feature_search.presentation.searchView.SearchFragment
import dagger.Component

@Component(modules = [SearchFragmentModule::class])
interface SearchFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): SearchFragmentComponent
    }

    fun inject(searchFragment: SearchFragment)
}