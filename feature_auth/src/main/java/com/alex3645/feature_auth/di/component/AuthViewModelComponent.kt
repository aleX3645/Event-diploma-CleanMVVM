package com.alex3645.feature_conference_list.di.component

import com.alex3645.feature_conference_list.di.module.AuthViewModelModule
import com.alex3645.feature_conference_list.presentation.authView.AuthViewModel
import com.alex3645.feature_conference_list.presentation.registrationView.RegistrationViewModel
import dagger.Component

@Component(modules = [AuthViewModelModule::class])
interface AuthViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): AuthViewModelComponent
    }

    fun inject(authViewModel: AuthViewModel)
    fun inject(registrationViewModel: RegistrationViewModel)
}