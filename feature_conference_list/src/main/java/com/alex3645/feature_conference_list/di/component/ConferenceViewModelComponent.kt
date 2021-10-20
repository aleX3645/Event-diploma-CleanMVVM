package com.alex3645.feature_conference_list.di.component

import com.alex3645.feature_conference_list.di.module.ConferenceListViewModelModule
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerViewModel
import dagger.Component

@Component(modules = [ConferenceListViewModelModule::class])
interface ConferenceViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): ConferenceViewModelComponent
    }

    fun inject(conferenceRecyclerViewModel: ConferenceRecyclerViewModel)
}