package com.alex3645.feature_auth.usecase

import android.accounts.Account
import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.domain.repository.AuthRepository
import java.lang.Exception
import java.net.PasswordAuthentication
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val conferenceRepository: AuthRepository,
                                              private val context: Context){
    interface Result {
        data class Success(val regResponse: AccResponse) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(userRegJson: UserRegJson) : RegistrationUseCase.Result {
        return try{
            val authResponse = conferenceRepository.register(userRegJson)

            return Result.Success(authResponse)

        }catch (e: Exception){
            Result.Error(e)
        }
    }
}