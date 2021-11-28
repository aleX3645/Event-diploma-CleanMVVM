package com.alex3645.feature_ticket_list.data.network.service

import com.alex3645.feature_ticket_list.data.model.TicketJson
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofitTicketListService {
    @GET("api/usr/getTicketsForUserByLogin/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : List<TicketJson>
}