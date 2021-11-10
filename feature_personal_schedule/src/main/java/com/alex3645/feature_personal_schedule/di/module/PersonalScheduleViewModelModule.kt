package com.alex3645.feature_personal_schedule.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_personal_schedule.data.network.service.ApiRetrofitPersonalScheduleService
import com.alex3645.feature_personal_schedule.data.repositoryImpl.PersonalScheduleRepositoryImpl
import com.alex3645.feature_personal_schedule.domain.repository.PersonalScheduleRepository
import com.alex3645.feature_personal_schedule.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_personal_schedule.usecase.LoadPersonalEventsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class PersonalScheduleViewModelModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitPersonalScheduleService {
        return retrofit.create(ApiRetrofitPersonalScheduleService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitPersonalScheduleService): PersonalScheduleRepository {
        return PersonalScheduleRepositoryImpl(retrofitService)
    }

    @Provides
    fun provideLoadUserUseCase(repository: PersonalScheduleRepository): LoadAccountByLoginUseCase {
        return LoadAccountByLoginUseCase(repository)
    }

    @Provides
    fun provideLoadPersonalEventsUseCase(repository: PersonalScheduleRepository): LoadPersonalEventsUseCase {
        return LoadPersonalEventsUseCase(repository)
    }
}