package com.alex3645.feature_conference_builder.usecase

import com.alex3645.feature_conference_builder.domain.model.User
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import java.lang.Exception
import javax.inject.Inject

class LoadAccountByLoginUseCase @Inject constructor(private val repository: ConferenceBuilderRepository){

    interface Result {
        data class Success(val user: User) : Result
        data class Error(val e: Exception) : Result
    }

    suspend operator fun invoke(login: String): Result {
        return try{
            val user = repository.loadAccountByLogin(login)
            Result.Success(user)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}