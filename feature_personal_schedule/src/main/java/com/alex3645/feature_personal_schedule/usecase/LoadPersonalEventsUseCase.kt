package com.alex3645.feature_personal_schedule.usecase

import android.util.Log
import com.alex3645.feature_personal_schedule.domain.model.Event
import com.alex3645.feature_personal_schedule.domain.model.User
import com.alex3645.feature_personal_schedule.domain.repository.PersonalScheduleRepository
import java.lang.Exception
import javax.inject.Inject

class LoadPersonalEventsUseCase @Inject constructor(private val repository: PersonalScheduleRepository){

    interface Result {
        data class Success(val events: List<Event>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(token: String, id: Long): Result {
        return try{
            val events = repository.loadPersonalSchedule(token,id)
            Result.Success(events)
        }catch (e: Exception){
            Log.d("!!!", e?.message?:"null error")
            Result.Error(e)
        }
    }
}