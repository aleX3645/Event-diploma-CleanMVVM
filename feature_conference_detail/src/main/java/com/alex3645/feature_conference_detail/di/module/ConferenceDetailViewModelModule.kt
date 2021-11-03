package com.alex3645.feature_conference_detail.di.module

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_detail.data.network.service.ApiRetrofitConferenceDetailService
import com.alex3645.feature_conference_detail.data.repositoryImpl.ConferenceDetailRepositoryImpl
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import com.alex3645.feature_conference_detail.usecase.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ConferenceDetailViewModelModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitConferenceDetailService {
        return retrofit.create(ApiRetrofitConferenceDetailService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitConferenceDetailService): ConferenceDetailRepository {
        return ConferenceDetailRepositoryImpl(retrofitService)
    }

    @Provides
    fun provideConferenceByIdUseCase(conferenceRepository: ConferenceDetailRepository): LoadConferenceByIdUseCase {
        return LoadConferenceByIdUseCase(conferenceRepository)
    }

    @Provides
    fun provideEventsByConferenceUseCase(conferenceRepository: ConferenceDetailRepository): LoadEventsForConferenceWithIdUseCase {
        return LoadEventsForConferenceWithIdUseCase(conferenceRepository)
    }

    @Provides
    fun provideSendMessageUseCase(conferenceRepository: ConferenceDetailRepository): SendMessageUseCase {
        return SendMessageUseCase(conferenceRepository)
    }

    @Provides
    fun provideLoadChatUseCase(conferenceRepository: ConferenceDetailRepository): LoadChatUseCase {
        return LoadChatUseCase(conferenceRepository)
    }

    @Provides
    fun provideConnectToChatUseCase(conferenceRepository: ConferenceDetailRepository, gson: Gson) : ConnectToChatUseCase {
        return ConnectToChatUseCase(conferenceRepository, gson)
    }

    @Provides
    fun provideEventById(conferenceRepository: ConferenceDetailRepository): LoadEventByIdUseCase {
        return LoadEventByIdUseCase(conferenceRepository)
    }
}