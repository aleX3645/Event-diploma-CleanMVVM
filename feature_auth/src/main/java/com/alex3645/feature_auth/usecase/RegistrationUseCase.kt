package com.alex3645.feature_auth.usecase

import android.content.Context
import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.domain.repository.AuthRepository
import java.lang.Exception
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val conferenceRepository: AuthRepository){
    interface Result {
        data class Success(val regResponse: AccResponse) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(userRegJson: UserRegJson, orgFlag: Boolean) : Result {
        return if(orgFlag){
            try{
                val authResponse = conferenceRepository.registerAsOrganiser(userRegJson)
                Result.Success(authResponse)
            }catch (e: Exception){
                Result.Error(e)
            }
        }else{
            try{
                val authResponse = conferenceRepository.registerAsUser(userRegJson)
                Result.Success(authResponse)
            }catch (e: Exception){
                Result.Error(e)
            }
        }
    }
}