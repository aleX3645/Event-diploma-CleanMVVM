package com.alex3645.feature_conference_builder.usecase

import android.util.Log
import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import com.google.gson.Gson
import java.lang.Exception
import javax.inject.Inject

class SaveConferenceUseCase @Inject constructor(private val repository: ConferenceBuilderRepository){
    interface Result {
        data class Success(val response: Response) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(conference: Conference) : Result{
        return try{
            Log.d("!!!", conference.dateStart?: "nothing")
            Log.d("!!!", conference.dateEnd?: "nothing")
            Log.d("!!!", Gson().toJson(conference).toString())
            val response = repository.addConference(conference)
            Log.d("!!!", response.success.toString())
            Result.Success(response)
        }catch (e: Exception){
            e.message?.let { Log.d("!!!", it) }
            Result.Error(e)
        }
    }
}