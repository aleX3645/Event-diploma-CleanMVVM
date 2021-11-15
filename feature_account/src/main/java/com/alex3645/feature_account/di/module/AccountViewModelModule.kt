package com.alex3645.feature_account.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alex3645.app.data.api.AppConstants
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_account.data.database.AccountDatabase
import com.alex3645.feature_account.data.database.AccountDao
import com.alex3645.feature_account.data.network.service.ApiRetrofitAccountService
import com.alex3645.feature_account.data.repositoryImpl.AccountRepositoryImpl
import com.alex3645.feature_account.domain.repository.AccountRepository
import com.alex3645.feature_account.usecase.EditAccountUseCase
import com.alex3645.feature_account.usecase.LoadAccountByLoginUseCase
import com.alex3645.feature_account.usecase.RemoveAccountUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AccountViewModelModule (private val context: Context) {

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
    fun provideAccountsDao(roomDatabase: AccountDatabase) : AccountDao =
        roomDatabase.accounts()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(ServerConstants.LOCAL_SERVER)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideApiAuthService(retrofit: Retrofit): ApiRetrofitAccountService {
        return retrofit.create(ApiRetrofitAccountService::class.java)
    }

    @Provides
    fun provideRepository(retrofitService: ApiRetrofitAccountService, accountDao: AccountDao): AccountRepository {
        return AccountRepositoryImpl(accountDao, retrofitService)
    }

    @Provides
    fun provideRemoveAccountUseCase(accountRepository: AccountRepository) : RemoveAccountUseCase{
        return RemoveAccountUseCase(accountRepository, context)
    }

    @Provides
    fun provideLoadAccountUseCase(accountRepository: AccountRepository) : LoadAccountByLoginUseCase{
        return LoadAccountByLoginUseCase(accountRepository)
    }

    @Provides
    fun provideEditAccountUseCase(accountRepository: AccountRepository) : EditAccountUseCase{
        return EditAccountUseCase(accountRepository, context)
    }
}