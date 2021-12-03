package com.alex3645.feature_ticket_list.data.repositoryImpl

import android.util.Log
import com.alex3645.feature_ticket_list.data.model.ConferenceJson
import com.alex3645.feature_ticket_list.data.model.TariffJson
import com.alex3645.feature_ticket_list.data.network.service.ApiRetrofitTicketListService
import com.alex3645.feature_ticket_list.domain.model.Conference
import com.alex3645.feature_ticket_list.domain.model.Tariff
import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import com.alex3645.feature_ticket_list.domain.repository.TicketListRepository
import javax.inject.Inject

class TicketListRepositoryImpl @Inject constructor(private var service: ApiRetrofitTicketListService):
    TicketListRepository {
    override suspend fun loadTicketsByLogin(login: String): List<Ticket> {
        return service.loadUserByLogin(login).map { it.toDomainModel() }
    }

    override suspend fun getTicketsInfoForUserWithLogin(login: String): List<TicketInfo> {
        Log.d("!!!", login)
        return service.getTicketsInfoForUserWithLogin(login).map { it.toDomainModel() }
    }

}