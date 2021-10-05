package com.alex3645.feature_account.usecase

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_auth.data.database.AccountDao
import com.alex3645.feature_auth.data.database.model.AccountEntity
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject

class LoadAuthAccountUseCase @Inject constructor(private val accountDao: AccountDao){
    interface Result {
        data class Success(val accountEntity: AccountEntity) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(): Result {
        return try{
            Result.Success(accountDao.getAll().first())
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}