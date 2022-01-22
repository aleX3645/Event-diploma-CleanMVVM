package com.alex3645.feature_conference_builder.usecase

import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import java.lang.Exception
import javax.inject.Inject

class LoadConferenceByIdUseCase @Inject constructor(private val repository: ConferenceBuilderRepository){
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