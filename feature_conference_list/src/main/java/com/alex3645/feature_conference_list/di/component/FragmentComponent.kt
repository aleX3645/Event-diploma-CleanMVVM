package com.alex3645.feature_conference_list.di.component


import com.alex3645.feature_conference_list.di.module.DemoModule
import com.alex3645.feature_conference_list.di.module.FragmentModule
import com.alex3645.feature_conference_list.di.module.ViewModelInjectionModule
import com.alex3645.feature_conference_list.di.module.ViewModelModule
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerFragment
import dagger.Component

@Component(modules = [FragmentModule::class, ViewModelModule::class, ViewModelInjectionModule::class, DemoModule::class])
interface FragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): FragmentComponent
    }

    fun inject(conferenceRecyclerFragment: ConferenceRecyclerFragment)
}