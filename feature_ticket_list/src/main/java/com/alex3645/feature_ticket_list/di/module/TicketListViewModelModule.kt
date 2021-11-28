package com.alex3645.feature_ticket_list.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_ticket_list.data.network.service.ApiRetrofitTicketListService
import com.alex3645.feature_ticket_list.data.repositoryImpl.TicketListRepositoryImpl
import com.alex3645.feature_ticket_list.domain.repository.TicketListRepository
import com.alex3645.feature_ticket_list.usecase.LoadTicketsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class TicketListViewModelModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitTicketListService {
        return retrofit.create(ApiRetrofitTicketListService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitTicketListService): TicketListRepository {
        return TicketListRepositoryImpl(retrofitService)
    }

    @Provides
    fun provideLoadTicketsUseCase(repository: TicketListRepository): LoadTicketsUseCase {
        return LoadTicketsUseCase(repository)
    }
}