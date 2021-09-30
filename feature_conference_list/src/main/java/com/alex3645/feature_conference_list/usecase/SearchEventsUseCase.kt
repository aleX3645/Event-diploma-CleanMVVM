package com.alex3645.feature_conference_list.usecase

import android.util.Log
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import javax.inject.Inject

class SearchEventsUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository) {
    interface Result {
        data class Success(val eventList: List<Event>) : Result
        data class Error(val e: Throwable) : Result
    }


    suspend operator fun invoke(eventName: String) : Result{
        return try{
            val eventList = conferenceRepository.searchEvents(eventName)
            Log.d("!!!", eventList.size.toString())
            return Result.Success(eventList)

        }catch (e: Exception){
            throw e
            Result.Error(e)
        }
        return Result.Error(Throwable())
    }
}