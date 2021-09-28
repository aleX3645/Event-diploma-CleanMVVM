package com.alex3645.feature_conference_list.usecase

import android.provider.Settings
import com.alex3645.feature_conference_list.data.model.AccResponse
import com.alex3645.feature_conference_list.data.model.AuthRequest
import com.alex3645.feature_conference_list.data.model.UserRegJson
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import java.lang.Exception
import java.net.PasswordAuthentication
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository){
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