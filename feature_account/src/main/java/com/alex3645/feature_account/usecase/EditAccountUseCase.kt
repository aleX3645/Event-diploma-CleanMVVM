package com.alex3645.feature_account.usecase

import android.content.Context
import android.util.Log
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.feature_account.domain.model.User
import com.alex3645.feature_account.domain.repository.AccountRepository
import java.lang.Exception
import javax.inject.Inject

class EditAccountUseCase @Inject constructor(private val repository: AccountRepository, private val context: Context){

    interface Result {
        object Success : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(login: String, user: User): Result {
        return try{
            val spManager = SharedPreferencesManager(context)
            repository.editAccountWithLogin(spManager.fetchAuthToken()!!,login,user)
            Result.Success
        }catch (e: Exception){
            Log.d("!!!", e.message.toString())
            Result.Error(e)
        }
    }
}