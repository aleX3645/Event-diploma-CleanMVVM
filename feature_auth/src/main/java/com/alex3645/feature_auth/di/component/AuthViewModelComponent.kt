package com.alex3645.feature_auth.di.component

import com.alex3645.feature_auth.di.module.AuthViewModelModule
import com.alex3645.feature_auth.presentation.authView.AuthViewModel
import com.alex3645.feature_auth.presentation.registrationView.RegistrationViewModel
import dagger.Component

@Component(modules = [AuthViewModelModule::class])
interface AuthViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(authViewModelModule: AuthViewModelModule): AuthViewModelComponent
    }

    fun inject(authViewModel: AuthViewModel)
    fun inject(registrationViewModel: RegistrationViewModel)
}