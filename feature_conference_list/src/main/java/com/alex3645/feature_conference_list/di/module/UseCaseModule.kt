package com.alex3645.feature_conference_list.di.module

import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.di.scope.ConfListModule
import com.alex3645.feature_conference_list.usecase.LoadConferencesUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {
    @Provides
    @ConfListModule
    fun provideListApiConfService(): ApiRetrofitConferenceService {
        return ApiRetrofitConferenceService.create()
    }

    @Provides
    fun provideLoadConferencesUseCase(): LoadConferencesUseCase {
        return LoadConferencesUseCase(provideListApiConfService())
    }
}