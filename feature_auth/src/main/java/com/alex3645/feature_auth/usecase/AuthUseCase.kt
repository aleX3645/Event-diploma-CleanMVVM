package com.alex3645.feature_auth.usecase

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.domain.repository.AuthRepository
import java.lang.Exception
import java.net.PasswordAuthentication
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val conferenceRepository: AuthRepository,
                                      private val context: Context){
    interface Result {
        data class Success(val authResponse: AccResponse) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(authentication: PasswordAuthentication) : Result{
        return try{
            val authResponse = conferenceRepository.auth(AuthRequest(Settings.Secure.ANDROID_ID,authentication.userName,String(authentication.password)))
            Result.Success(authResponse)
        }catch (e: Exception){
            e.message?.let { Log.d("!!!", it) }
            Result.Error(e)
        }
    }
}