package com.alex3645.feature_conference_detail.usecase

import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import java.lang.Exception
import javax.inject.Inject

class LoadEventsForEventWithIdUseCase @Inject constructor(private val repository: ConferenceDetailRepository){
    interface Result {
        data class Success(val data: List<Event>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(id: Int) : Result{
        return try{
            val events: List<Event> = repository.loadEventsForEventWithId(id)
            Result.Success(events)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}