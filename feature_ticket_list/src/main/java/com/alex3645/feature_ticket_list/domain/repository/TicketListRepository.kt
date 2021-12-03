package com.alex3645.feature_ticket_list.domain.repository

import com.alex3645.feature_ticket_list.data.model.ConferenceJson
import com.alex3645.feature_ticket_list.data.model.TariffJson
import com.alex3645.feature_ticket_list.domain.model.Conference
import com.alex3645.feature_ticket_list.domain.model.Tariff
import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface TicketListRepository {
    suspend fun loadTicketsByLogin(login: String) : List<Ticket>
    suspend fun getTicketsInfoForUserWithLogin(login: String): List<TicketInfo>
}