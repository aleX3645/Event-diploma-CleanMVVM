package com.alex3645.feature_conference_detail.usecase

import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import java.lang.Exception
import javax.inject.Inject

class LoadEventByIdUseCase @Inject constructor(private val repository: ConferenceDetailRepository){
    interface Result {
        data class Success(val data: Event) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(id: Int) : Result{
        return try{
            val event: Event = repository.loadEventById(id)
            Result.Success(event)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}