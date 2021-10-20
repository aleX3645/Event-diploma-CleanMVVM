package com.alex3645.feature_conference_list.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.data.repositoryImpl.ConferenceRepositoryImpl
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import com.alex3645.feature_conference_list.usecase.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ConferenceListViewModelModule {

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideListApiConfService(retrofit: Retrofit): ApiRetrofitConferenceService {
        return retrofit.create(ApiRetrofitConferenceService::class.java)
    }

    @Provides
    fun provideRepository(apiRetrofitConferenceService: ApiRetrofitConferenceService): ConferenceRepository {
        return ConferenceRepositoryImpl(apiRetrofitConferenceService)
    }

    @Provides
    fun provideConferenceUseCase(conferenceRepository: ConferenceRepository): LoadNextConferencesUseCase {
        return LoadNextConferencesUseCase(conferenceRepository)
    }
}