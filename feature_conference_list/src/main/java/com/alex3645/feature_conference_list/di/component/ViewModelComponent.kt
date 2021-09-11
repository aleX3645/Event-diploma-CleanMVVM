package com.alex3645.feature_conference_list.di.component

import com.alex3645.feature_conference_list.di.module.ViewModelModule
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerFragment
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerViewModel
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): ViewModelComponent
    }

    fun inject(conferenceRecyclerViewModel: ConferenceRecyclerViewModel)
}