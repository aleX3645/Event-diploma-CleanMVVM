package com.alex3645.feature_conference_detail.usecase

import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import java.lang.Exception
import javax.inject.Inject

class LoadConferenceByIdUseCase @Inject constructor(private val repository: ConferenceDetailRepository){
    interface Result {
        data class Success(val data: Conference) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(id: Int) : Result{
        return try{
            val conference: Conference = repository.loadConferenceById(id)
            Result.Success(conference)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}