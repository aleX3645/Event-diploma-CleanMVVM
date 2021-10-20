package com.alex3645.feature_search.usecase

import com.alex3645.feature_search.domain.data.Conference
import com.alex3645.feature_search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchConferencesUseCase @Inject constructor(private val searchRepository: SearchRepository){
    interface Result {
        data class Success(val confList: List<Conference>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(conferenceName: String) : Result {
       return try{
           val conferenceList = searchRepository.searchConferences(conferenceName)
           Result.Success(conferenceList)
        }catch (e: Exception){
           Result.Error(e)
        }
    }
}