package com.alex3645.feature_auth.usecase

import android.provider.Settings
import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.domain.repository.AuthRepository
import java.lang.Exception
import java.net.PasswordAuthentication
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val conferenceRepository: AuthRepository){
    interface Result {
        data class Success(val regResponse: AccResponse) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(userRegJson: UserRegJson) : RegistrationUseCase.Result {
        return try{
            val authResponse = conferenceRepository.register(userRegJson)

            return RegistrationUseCase.Result.Success(authResponse)

        }catch (e: Exception){
            RegistrationUseCase.Result.Error(e)
        }
    }
}