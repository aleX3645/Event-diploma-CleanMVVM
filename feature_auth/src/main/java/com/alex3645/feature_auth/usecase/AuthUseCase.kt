package com.alex3645.feature_auth.usecase

import android.provider.Settings
import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.domain.repository.AuthRepository
import java.lang.Exception
import java.net.PasswordAuthentication
import javax.inject.Inject

class AuthUseCase @Inject constructor(private val conferenceRepository: AuthRepository){

    interface Result {
        data class Success(val authResponse: AccResponse) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(authentication: PasswordAuthentication) : Result{
        return try{
            val authResponse = conferenceRepository.auth(AuthRequest(Settings.Secure.ANDROID_ID,authentication.userName,authentication.password.toString()))

            return Result.Success(authResponse)

        }catch (e: Exception){
            Result.Error(e)
        }
    }
}