package com.alex3645.feature_conference_list.di.module

import androidx.lifecycle.ViewModelProvider
import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.data.network.service.MainApiServerAddress
import com.alex3645.feature_conference_list.di.util.ViewModelFactory
import com.alex3645.feature_conference_list.domain.remote.ConferenceRemoteDataSource
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import com.alex3645.feature_conference_list.usecase.AuthUseCase
import com.alex3645.feature_conference_list.usecase.LoadNextConferencesUseCase
import com.alex3645.feature_conference_list.usecase.RegistrationUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(MainApiServerAddress.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

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

    @Provides
    fun provideAuthUseCase(conferenceRepository: ConferenceRepository): AuthUseCase {
        return AuthUseCase(conferenceRepository)
    }

    @Provides
    fun provideRegUseCase(conferenceRepository: ConferenceRepository): RegistrationUseCase {
        return RegistrationUseCase(conferenceRepository)
    }


}