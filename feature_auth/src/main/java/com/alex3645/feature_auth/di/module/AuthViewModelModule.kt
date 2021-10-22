package com.alex3645.feature_auth.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alex3645.app.data.api.AppConstants
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_auth.data.network.service.ApiRetrofitAuthService
import com.alex3645.feature_auth.data.repositoryImpl.AuthRepositoryImpl
import com.alex3645.feature_auth.domain.repository.AuthRepository
import com.alex3645.feature_auth.usecase.AuthUseCase
import com.alex3645.feature_auth.usecase.RegistrationUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.alex3645.feature_auth.data.database.AccountDatabase
import com.alex3645.feature_auth.data.database.AccountAuthDao
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Module
class AuthViewModelModule(val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideRoomBuilder(context: Context) = Room.databaseBuilder(
        context.applicationContext,
        AccountDatabase::class.java, AppConstants.ACCOUNT_DB_NAME
    )

    @Provides
    fun provideRoomDB(builder: RoomDatabase.Builder<AccountDatabase>) : AccountDatabase{
        val factory = SupportFactory(SQLiteDatabase.getBytes(AppConstants.DB_CODE_PHRASE.toCharArray()))
        builder.openHelperFactory(factory)
        return builder.build()
    }

    @Provides
    fun provideAccountsDao(roomDatabase: AccountDatabase) : AccountAuthDao =
        roomDatabase.accounts()

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
    fun provideRepository(retrofitService: ApiRetrofitAuthService, accountDao: AccountAuthDao): AuthRepository {
        return AuthRepositoryImpl(retrofitService,accountDao)
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