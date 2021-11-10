package com.alex3645.feature_personal_schedule.usecase

import com.alex3645.feature_personal_schedule.domain.model.User
import com.alex3645.feature_personal_schedule.domain.repository.PersonalScheduleRepository
import java.lang.Exception
import javax.inject.Inject

class LoadAccountByLoginUseCase @Inject constructor(private val repository: PersonalScheduleRepository){

    interface Result {
        data class Success(val user: User) : Result
        data class Error(val e: Throwable) : Result
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