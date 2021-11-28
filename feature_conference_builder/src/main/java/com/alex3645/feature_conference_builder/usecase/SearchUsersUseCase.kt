package com.alex3645.feature_conference_builder.usecase

import com.alex3645.feature_conference_builder.domain.model.User
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val repository: ConferenceBuilderRepository) {
    interface Result {
        data class Success(val userList: List<User>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(conferenceName: String) : Result {
        return try{
            val userList = repository.searchUsers(conferenceName)
            Result.Success(userList)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}