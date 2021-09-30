package com.alex3645.feature_conference_list.di.component

import com.alex3645.feature_conference_list.di.module.ViewModelModule
import com.alex3645.feature_conference_list.presentation.authView.AuthFragment
import com.alex3645.feature_conference_list.presentation.authView.AuthViewModel
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerFragment
import com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.ConferenceRecyclerViewModel
import com.alex3645.feature_conference_list.presentation.registrationView.RegistrationViewModel
import com.alex3645.feature_conference_list.presentation.searchView.SearchViewModel
import dagger.Component

@Component(modules = [ViewModelModule::class])
interface ViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): ViewModelComponent
    }

    fun inject(conferenceRecyclerViewModel: ConferenceRecyclerViewModel)
    fun inject(authViewModel: AuthViewModel)
    fun inject(registrationViewModel: RegistrationViewModel)
    fun inject(searchViewModel: SearchViewModel)
}