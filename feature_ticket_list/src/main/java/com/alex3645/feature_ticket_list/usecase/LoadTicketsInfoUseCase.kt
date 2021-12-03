package com.alex3645.feature_ticket_list.usecase

import com.alex3645.feature_ticket_list.domain.model.Conference
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import com.alex3645.feature_ticket_list.domain.repository.TicketListRepository
import java.lang.Exception
import javax.inject.Inject

class LoadTicketsInfoUseCase @Inject constructor(private val repository: TicketListRepository){
    interface Result {
        data class Success(val data: List<TicketInfo>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(login: String) : Result{
        return try{
            val ticketInfoList = repository.getTicketsInfoForUserWithLogin(login)
            Result.Success(ticketInfoList)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}