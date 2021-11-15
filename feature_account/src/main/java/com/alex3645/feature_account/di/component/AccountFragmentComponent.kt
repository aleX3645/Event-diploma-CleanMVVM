package com.alex3645.feature_account.di.component

import com.alex3645.feature_account.di.module.AccountFragmentModule
import com.alex3645.feature_account.presentation.settingsView.SettingsFragment
import dagger.Component

@Component(modules = [AccountFragmentModule::class])
interface AccountFragmentComponent {
    @Component.Factory
    interface Factory {
        fun create(): AccountFragmentComponent
    }

    fun inject(settingsFragment: SettingsFragment)
}