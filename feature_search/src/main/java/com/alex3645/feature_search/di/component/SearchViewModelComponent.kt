package com.alex3645.feature_search.di.component

import com.alex3645.feature_search.di.module.SearchViewModelModule
import com.alex3645.feature_search.presentation.searchView.SearchViewModel
import dagger.Component

@Component(modules = [SearchViewModelModule::class])
interface SearchViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): SearchViewModelComponent
    }

    fun inject(searchViewModel: SearchViewModel)
}