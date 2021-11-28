package com.alex3645.feature_conference_detail.usecase

import com.alex3645.feature_conference_detail.domain.model.User
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import javax.inject.Inject

class LoadAccountByIdUseCase @Inject constructor(
    private val repository: ConferenceDetailRepository
){
    interface Result {
        data class Success(val user: User) : Result
        data class Error(val e: Exception) : Result
    }

    suspend operator fun invoke(id: Int) : Result {
        return try{
            val user = repository.loadUserById(id)
            Result.Success(user)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}