package com.alex3645.feature_conference_detail.usecase

import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import java.lang.Exception
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val repository: ConferenceDetailRepository){
    interface Result {
        object Success : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(chatMessage: ChatMessage) : Result{
        return try{
            repository.sendChatMessage(chatMessage)
            Result.Success
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}