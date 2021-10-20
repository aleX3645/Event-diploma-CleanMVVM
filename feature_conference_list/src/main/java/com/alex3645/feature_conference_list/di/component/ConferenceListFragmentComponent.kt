package com.alex3645.feature_conference_list.di.component


import com.alex3645.feature_conference_list.di.module.ConferenceListFragmentModule
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerFragment
import dagger.Component

@Component(modules = [ConferenceListFragmentModule::class])
interface ConferenceListFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): ConferenceListFragmentComponent
    }

    fun inject(conferenceRecyclerFragment: ConferenceRecyclerFragment)
}