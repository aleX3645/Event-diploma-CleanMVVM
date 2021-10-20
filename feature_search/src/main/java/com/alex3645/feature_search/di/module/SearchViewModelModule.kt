package com.alex3645.feature_search.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_search.data.network.ApiRetrofitSearchService
import com.alex3645.feature_search.data.repositoryImpl.SearchRepositoryImpl
import com.alex3645.feature_search.domain.repository.SearchRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class SearchViewModelModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitSearchService {
        return retrofit.create(ApiRetrofitSearchService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitSearchService): SearchRepository {
        return SearchRepositoryImpl(retrofitService)
    }

    @Provides
    fun provideSearchConferencesUseCase(searchRepository: SearchRepository): com.alex3645.feature_search.usecase.SearchConferencesUseCase {
        return com.alex3645.feature_search.usecase.SearchConferencesUseCase(searchRepository)
    }

    @Provides
    fun provideSearchEventsUseCase(searchRepository: SearchRepository): com.alex3645.feature_search.usecase.SearchEventsUseCase {
        return com.alex3645.feature_search.usecase.SearchEventsUseCase(searchRepository)
    }

    @Provides
    fun provideSearchUsersUseCase(searchRepository: SearchRepository): com.alex3645.feature_search.usecase.SearchUsersUseCase {
        return com.alex3645.feature_search.usecase.SearchUsersUseCase(searchRepository)
    }
}