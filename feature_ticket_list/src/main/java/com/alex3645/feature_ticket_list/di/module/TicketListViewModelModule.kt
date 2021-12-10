package com.alex3645.feature_ticket_list.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_ticket_list.data.network.service.ApiRetrofitTicketListService
import com.alex3645.feature_ticket_list.data.repositoryImpl.TicketListRepositoryImpl
import com.alex3645.feature_ticket_list.domain.repository.TicketListRepository
import com.alex3645.feature_ticket_list.usecase.LoadTicketsInfoUseCase
import com.alex3645.feature_ticket_list.usecase.LoadTicketsUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.startActivity
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException


@Module
class TicketListViewModelModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideClient(): OkHttpClient{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response? {
                    val request: Request = chain.request()
                    val response: Response = chain.proceed(request)

                    // todo deal with the issues the way you need to
                    if (response.code() == 500) {

                        throw IOException()
                    }
                    return response
                }
            })
            .build()
        return okHttpClient
    }

    @Provides
    fun provideRetrofit(gson: Gson, client: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        //.client(client)
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

    @Provides
    fun provideLoadConferenceByIdUseCase(repository: TicketListRepository): LoadTicketsInfoUseCase {
        return LoadTicketsInfoUseCase(repository)
    }
}