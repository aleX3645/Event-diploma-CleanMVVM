package com.alex3645.feature_ticket_list.data.network.service

import com.alex3645.feature_ticket_list.data.model.ConferenceJson
import com.alex3645.feature_ticket_list.data.model.TariffJson
import com.alex3645.feature_ticket_list.data.model.TicketInfoJson
import com.alex3645.feature_ticket_list.data.model.TicketJson
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofitTicketListService {
    @GET("api/usr/getTicketsForUserByLogin/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : List<TicketJson>

    @GET("/api/usr/getTicketsInfoForUserWithLogin/{login}")
    suspend fun getTicketsInfoForUserWithLogin(@Path("login") login: String): List<TicketInfoJson>
}