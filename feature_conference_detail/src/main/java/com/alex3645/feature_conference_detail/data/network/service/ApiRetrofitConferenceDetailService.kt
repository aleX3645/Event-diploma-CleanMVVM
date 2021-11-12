package com.alex3645.feature_conference_detail.data.network.service

import com.alex3645.feature_conference_detail.data.model.*
import retrofit2.http.*

interface ApiRetrofitConferenceDetailService {

    @GET("api/usr/info/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : UserJson

    @GET("/api/usr/conference/{id}")
    suspend fun getConferenceById(@Path("id") id: Int): ConferenceJson

    @GET("/api/usr/event/{id}")
    suspend fun getEventById(@Path("id") id: Int): EventJson

    @GET("/api/usr/conferenceEvents/{id}")
    suspend fun getEventsForConferenceWithId(@Path("id") id: Int): List<EventJson>

    @GET("/api/usr/childrenEvents/{id}")
    suspend fun getEventsForEventWithId(@Path("id") id: Int): List<EventJson>

    @GET("/api/usr/chatMessagesForConference/{id}")
    suspend fun getChatByConferenceId(@Path("id") id: Long): List<ChatMessage>

    @POST("/api/chat/sendMessage")
    suspend fun sendChatMessage(@Body chatMessage: ChatMessage)

    @POST("/api/usr/registerTicket/{buyerId}")
    suspend fun registerTicket(@Header("token") token: String, @Path("buyerId") id: Long, @Body ticket: TicketJson)
}