package com.alex3645.feature_conference_list.usecase

import android.provider.Settings
import com.alex3645.feature_conference_list.data.model.AccResponse
import com.alex3645.feature_conference_list.data.model.AuthRequest
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.domain.model.User
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import java.lang.Exception
import java.net.PasswordAuthentication
import javax.inject.Inject

class SearchConferencesUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository){
    interface ResultConferences {
        data class SuccessConferences(val confList: List<Conference>) : ResultConferences
        data class Error(val e: Throwable) : ResultConferences
    }


    suspend fun findConferences(conferenceName: String) : ResultConferences{
       /* return try{
            val authResponse = conferenceRepository.auth(AuthRequest(Settings.Secure.ANDROID_ID,authentication.userName,authentication.password.toString()))

            return Result.Success(authResponse)

        }catch (e: Exception){
            Result.Error(e)
        }*/
        return ResultConferences.Error(Throwable())
    }
}