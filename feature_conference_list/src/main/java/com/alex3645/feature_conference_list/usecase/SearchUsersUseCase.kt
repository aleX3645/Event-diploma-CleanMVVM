package com.alex3645.feature_conference_list.usecase

import android.util.Log
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.domain.model.User
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import javax.inject.Inject

class SearchUsersUseCase @Inject constructor(private val conferenceRepository: ConferenceRepository) {
    interface Result {
        data class Success(val userList: List<User>) : Result
        data class Error(val e: Throwable) : Result
    }


    suspend operator fun invoke(conferenceName: String) : Result{
        return try{
            val userList = conferenceRepository.searchUsers(conferenceName)
            Log.d("!!!", userList.size.toString())
            return Result.Success(userList)

        }catch (e: Exception){
            throw e
            Result.Error(e)
        }
        return Result.Error(Throwable())
    }
}