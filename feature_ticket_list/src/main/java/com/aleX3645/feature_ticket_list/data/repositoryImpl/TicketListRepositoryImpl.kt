package com.alex3645.feature_ticket_list.data.repositoryImpl

import com.alex3645.feature_ticket_list.data.network.service.ApiRetrofitTicketListService
import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.domain.repository.TicketListRepository
import javax.inject.Inject

class TicketListRepositoryImpl @Inject constructor(private var service: ApiRetrofitTicketListService):
    TicketListRepository {
    override suspend fun loadTicketsByLogin(login: String): List<Ticket> {
        return service.loadUserByLogin(login).map { it.toDomainModel() }
    }
}