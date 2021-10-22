package com.alex3645.feature_account.di.component

import com.alex3645.feature_account.di.module.AccountViewModelModule
import com.alex3645.feature_account.presentation.accountView.AccountViewModel
import com.alex3645.feature_account.presentation.editAccountView.EditAccountViewModel
import com.alex3645.feature_account.presentation.settingsView.SettingsViewModel
import dagger.Component

@Component(modules = [AccountViewModelModule::class])
interface AccountViewModelComponent {
    @Component.Factory
    interface Factory {
        fun create(accountViewModelModule: AccountViewModelModule): AccountViewModelComponent
    }

    fun inject(accountViewModel: AccountViewModel)
    fun inject(settingsViewModel: SettingsViewModel)
    fun inject(editAccountViewModel: EditAccountViewModel)
}