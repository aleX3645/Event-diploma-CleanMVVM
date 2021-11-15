package com.alex3645.feature_account.di.module

import com.alex3645.feature_account.presentation.settingsView.recyclerView.SettingsAdapter
import dagger.Module
import dagger.Provides

@Module
class AccountFragmentModule {
    @Provides
    fun provideSettingsAdapter() : SettingsAdapter {
        return SettingsAdapter()
    }
}