package com.alex3645.feature_conference_builder.usecase

import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import java.lang.Exception
import javax.inject.Inject

class UpdateConferenceUseCase @Inject constructor(private val repository: ConferenceBuilderRepository){
    interface Result {
        data class Success(val response: Response) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(token: String , conference: Conference) : Result{
        return try{
            val response = repository.changeConference(token, conference.id, conference.toJson())
            Result.Success(response)
        }catch (e: Exception){
            throw e
            Result.Error(e)
        }
    }
}