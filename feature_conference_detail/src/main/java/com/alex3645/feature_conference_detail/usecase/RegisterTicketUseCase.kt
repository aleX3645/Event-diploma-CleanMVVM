package com.alex3645.feature_conference_detail.usecase

import android.util.Log
import com.alex3645.feature_conference_detail.domain.model.Ticket
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import java.lang.Exception
import javax.inject.Inject

class RegisterTicketUseCase @Inject constructor(private val repository: ConferenceDetailRepository){
    interface Result {
        object Success : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(ticket: Ticket, id: Long, token: String) : Result{
        return try{
            repository.registerTicket(
                ticket = ticket,
                id = id,
                token = token
            )
            Log.d("!!!","after post ticket")
            Result.Success
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}