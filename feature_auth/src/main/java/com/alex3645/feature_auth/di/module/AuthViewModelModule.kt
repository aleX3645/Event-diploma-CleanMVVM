package com.alex3645.feature_conference_list.di.module

import androidx.lifecycle.ViewModelProvider
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_auth.data.network.service.ApiRetrofitAuthService
import com.alex3645.feature_auth.data.repositoryImpl.AuthRepositoryImpl
import com.alex3645.feature_auth.domain.repository.AuthRepository
import com.alex3645.feature_auth.usecase.AuthUseCase
import com.alex3645.feature_auth.usecase.RegistrationUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AuthViewModelModule {

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitAuthService {
        return retrofit.create(ApiRetrofitAuthService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitAuthService): AuthRepository {
        return AuthRepositoryImpl(retrofitService)
    }

    @Provides
    fun provideAuthUseCase(conferenceRepository: AuthRepository): AuthUseCase {
        return AuthUseCase(conferenceRepository)
    }

    @Provides
    fun provideRegUseCase(conferenceRepository: AuthRepository): RegistrationUseCase {
        return RegistrationUseCase(conferenceRepository)
    }
}