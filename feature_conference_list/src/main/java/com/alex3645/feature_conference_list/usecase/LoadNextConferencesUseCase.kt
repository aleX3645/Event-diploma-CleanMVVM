package com.alex3645.feature_conference_list.usecase

import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import java.lang.Exception
import javax.inject.Inject

class LoadNextConferencesUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository) {
    interface Result {
        data class Success(val data: List<Conference>) : Result
        data class Error(val e: Throwable) : Result
    }

    private var pageNumber = 0
    private val pageSize = 5
    suspend operator fun invoke() : Result{
        return try{
            val confList: List<Conference> = conferenceRepository.getConferences(pageSize = pageSize, pageNumber = pageNumber)

            if(confList.isNotEmpty()){
                pageNumber++
            }

            Result.Success(confList)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    fun dropData(){
        pageNumber = 0
    }
}