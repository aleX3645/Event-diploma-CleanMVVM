package com.alex3645.feature_account.usecase

import android.content.Context
import android.util.Log
import com.alex3645.base.android.SharedPreferencesManager
import com.alex3645.feature_account.domain.model.User
import com.alex3645.feature_account.domain.repository.AccountRepository
import java.lang.Exception
import javax.inject.Inject

class LoadAccountByLoginUseCase @Inject constructor(
    private val repository: AccountRepository,
    private val context: Context){

    interface Result {
        data class Success(val user: User) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(login: String): Result {
        return try{
            val user = repository.loadAccountByLogin(login)
            Result.Success(user)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}