package com.alex3645.feature_account.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alex3645.app.data.api.AppConstants
import com.alex3645.feature_account.data.database.AccountDatabase
import com.alex3645.feature_account.usecase.LoadAuthAccountUseCase
import com.alex3645.feature_auth.data.database.AccountDao
import dagger.Module
import dagger.Provides
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
class AccountViewModelModule (val context: Context) {

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
    fun provideLoadAuthAccountUseCase(accountDao: AccountDao) : LoadAuthAccountUseCase{
        return LoadAuthAccountUseCase(accountDao)
    }
}