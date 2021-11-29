package com.alex3645.feature_conference_detail.usecase

import android.util.Log
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import java.lang.Exception
import javax.inject.Inject

class LoadChatUseCase @Inject constructor(private val repository: ConferenceDetailRepository){
    interface Result {
        data class Success(val data: List<ChatMessage>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(id: Long) : Result{
        return try{
            Log.d("!!!",id.toString())
            val messages: List<ChatMessage> = repository.loadChatByConferenceId(id)
            Result.Success(messages)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}