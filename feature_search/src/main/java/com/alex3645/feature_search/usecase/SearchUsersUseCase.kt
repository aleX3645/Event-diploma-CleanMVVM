package com.alex3645.feature_search.usecase

import com.alex3645.feature_search.domain.data.User
import com.alex3645.feature_search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    interface Result {
        data class Success(val userList: List<User>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(conferenceName: String) : Result {
        return try{
            val userList = searchRepository.searchUsers(conferenceName)
            Result.Success(userList)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}