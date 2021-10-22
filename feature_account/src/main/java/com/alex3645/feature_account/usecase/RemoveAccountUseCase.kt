package com.alex3645.feature_account.usecase

import android.content.Context
import android.util.Log
import com.alex3645.base.android.SharedPreferencesManager
import com.alex3645.feature_account.data.database.AccountDao
import com.alex3645.feature_account.domain.model.User
import com.alex3645.feature_account.domain.repository.AccountRepository
import java.lang.Exception
import javax.inject.Inject

class RemoveAccountUseCase @Inject constructor(
    private val repository: AccountRepository,
    private val context: Context){
    interface Result {
        object Success : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(): Result {
        return try{
            val spManager = SharedPreferencesManager(context)
            spManager.removeUserData()

            repository.deleteAccountWithLogin(spManager.fetchLogin()?: "")

            Result.Success
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}