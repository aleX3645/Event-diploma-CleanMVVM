package com.alex3645.feature_conference_list.usecase

import android.util.Log
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import javax.inject.Inject

class SearchConferencesUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository){
    interface Result {
        data class Success(val confList: List<Conference>) : Result
        data class Error(val e: Throwable) : Result
    }


    suspend operator fun invoke(conferenceName: String) : Result{
       return try{
            val conferenceList = conferenceRepository.searchConferences(conferenceName)
            return Result.Success(conferenceList)

        }catch (e: Exception){
            Result.Error(e)
        }
        return Result.Error(Throwable())
    }
}