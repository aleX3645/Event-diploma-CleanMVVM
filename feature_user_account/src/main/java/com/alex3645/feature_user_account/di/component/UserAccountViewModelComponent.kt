package com.alex3645.feature_user_account.di.component

import com.alex3645.feature_user_account.di.module.UserAccountViewModelModule
import com.alex3645.feature_user_account.presentation.userAccountView.UserAccountViewModel
import dagger.Component

@Component(modules = [UserAccountViewModelModule::class])
interface UserAccountViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(): UserAccountViewModelComponent
    }

    fun inject(userAccountViewModel: UserAccountViewModel)
}