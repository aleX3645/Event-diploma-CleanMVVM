package com.alex3645.feature_conference_list.di.module

import androidx.lifecycle.ViewModelProvider
import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.data.network.service.MainApiServerAddress
import com.alex3645.feature_conference_list.di.util.ViewModelFactory
import com.alex3645.feature_conference_list.domain.remote.ConferenceRemoteDataSource
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    fun provideRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(MainApiServerAddress.LOCAL_SERVER)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun provideListApiConfService(retrofit: Retrofit): ApiRetrofitConferenceService {
        return retrofit.create(ApiRetrofitConferenceService::class.java)
    }

    @Provides
    fun provideRemote(retrofitService: ApiRetrofitConferenceService): ConferenceRemoteDataSource {
        return ConferenceRemoteDataSource(retrofitService)
    }

    @Provides
    fun provideRepository(conferenceRemoteDataSource: ConferenceRemoteDataSource): ConferenceRepository {
        return ConferenceRepository(conferenceRemoteDataSource)
    }

    @Provides
    fun provideConferenceUseCase(conferenceRepository: ConferenceRepository): LoadNextConferencesUseCase {
        return LoadNextConferencesUseCase(conferenceRepository)
    }


}