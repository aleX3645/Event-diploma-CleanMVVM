package com.alex3645.feature_user_account.usecase

import com.alex3645.feature_user_account.domain.data.User
import com.alex3645.feature_user_account.domain.repository.UserRepository
import javax.inject.Inject

class LoadUserByIdUseCase @Inject constructor(private val repository: UserRepository){
    interface Result {
        data class Success(val user: User) : Result
        data class Error(val e: Throwable) : Result
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