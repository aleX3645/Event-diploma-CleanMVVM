package com.alex3645.feature_conference_detail.usecase

import com.alex3645.feature_conference_detail.domain.model.User
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import javax.inject.Inject

class AddToPersonalScheduleUseCase @Inject constructor(
    private val repository: ConferenceDetailRepository
){
    interface Result {
        object Success : Result
        data class Error(val e: Exception) : Result
    }

    suspend operator fun invoke(token: String, userId: Int, eventId: Int) : Result {
        return try{
            repository.addToPersonalSchedule(token,userId.toLong(),eventId.toLong())
            Result.Success
        }catch (e: Exception){
            throw e
            Result.Error(e)
        }
    }
}