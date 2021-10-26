package com.alex3645.feature_conference_builder.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alex3645.app.data.api.AppConstants
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_builder.data.internet.service.ApiRetrofitBuilderService
import com.alex3645.feature_conference_builder.data.repositoryImpl.ConferenceBuilderRepositoryImpl
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import com.alex3645.feature_conference_builder.usecase.SaveConferenceUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class BuilderViewModelModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitBuilderService {
        return retrofit.create(ApiRetrofitBuilderService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitBuilderService): ConferenceBuilderRepository {
        return ConferenceBuilderRepositoryImpl(retrofitService,context)
    }

    @Provides
    fun provideSaveConferenceUseCase(conferenceRepository: ConferenceBuilderRepository): SaveConferenceUseCase {
        return SaveConferenceUseCase(conferenceRepository)
    }
}