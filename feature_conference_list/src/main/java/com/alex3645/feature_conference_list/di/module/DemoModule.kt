package com.alex3645.feature_conference_list.di.module

import androidx.lifecycle.ViewModel
import com.alex3645.feature_conference_list.di.util.ViewModelKey
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DemoModule {
    @Binds
    @IntoMap
    @ViewModelKey(ConferenceRecyclerViewModel::class)
    internal abstract fun conferenceRecyclerViewModel(conferenceRecyclerViewModel: ConferenceRecyclerViewModel): ViewModel
}