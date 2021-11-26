package com.alex3645.feature_user_account.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_user_account.data.network.ApiRetrofitAccountUserService
import com.alex3645.feature_user_account.data.repositoryImpl.UserRepositoryImpl
import com.alex3645.feature_user_account.domain.repository.UserRepository
import com.alex3645.feature_user_account.usecase.LoadPictureByUrlUseCase
import com.alex3645.feature_user_account.usecase.LoadUserByIdUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class UserAccountViewModelModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiRetrofitAccountUserService {
        return retrofit.create(ApiRetrofitAccountUserService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitAccountUserService): UserRepository {
        return UserRepositoryImpl(retrofitService)
    }

    @Provides
    fun provideLoadUserByIdUseCase(userRepository: UserRepository): LoadUserByIdUseCase {
        return LoadUserByIdUseCase(userRepository)
    }

    @Provides
    fun provideLoadPictureByUrlUseCase(): LoadPictureByUrlUseCase {
        return LoadPictureByUrlUseCase()
    }
}