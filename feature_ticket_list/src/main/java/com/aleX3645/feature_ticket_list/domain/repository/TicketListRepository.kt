package com.alex3645.feature_ticket_list.domain.repository

import com.alex3645.feature_ticket_list.domain.model.Ticket

interface TicketListRepository {
    suspend fun loadTicketsByLogin(login: String) : List<Ticket>
}