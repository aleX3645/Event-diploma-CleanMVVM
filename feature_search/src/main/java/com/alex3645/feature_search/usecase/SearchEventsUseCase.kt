package com.alex3645.feature_search.usecase

import com.alex3645.feature_search.domain.data.Event
import com.alex3645.feature_search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchEventsUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    interface Result {
        data class Success(val eventList: List<Event>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(eventName: String) : Result {
        return try{
            val eventList = searchRepository.searchEvents(eventName)
            return Result.Success(eventList)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}