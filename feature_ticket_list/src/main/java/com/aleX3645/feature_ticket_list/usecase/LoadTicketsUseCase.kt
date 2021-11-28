package com.alex3645.feature_ticket_list.usecase

import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.domain.repository.TicketListRepository
import java.lang.Exception
import javax.inject.Inject

class LoadTicketsUseCase @Inject constructor(private val repository: TicketListRepository) {

    interface Result {
        data class Success(val events: List<Ticket>) : Result
        data class Error(val e: Throwable) : Result
    }

    suspend operator fun invoke(login: String): Result {
        return try {
            val tickets = repository.loadTicketsByLogin(login)
            Result.Success(tickets)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}