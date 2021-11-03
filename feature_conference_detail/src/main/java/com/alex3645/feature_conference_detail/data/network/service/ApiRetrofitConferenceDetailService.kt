package com.alex3645.feature_conference_detail.data.network.service

import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.data.model.ConferenceJson
import com.alex3645.feature_conference_detail.data.model.EventJson
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiRetrofitConferenceDetailService {
    @GET("/api/usr/conference/{id}")
    suspend fun getConferenceById(@Path("id") id: Int): ConferenceJson

    @GET("/api/usr/event/{id}")
    suspend fun getEventById(@Path("id") id: Int): EventJson

    @GET("/api/usr/conferenceEvents/{id}")
    suspend fun getEventsForConferenceWithId(@Path("id") id: Int): List<EventJson>

    @GET("/api/usr/chatMessagesForConference/{id}")
    suspend fun getChatByConferenceId(@Path("id") id: Long): List<ChatMessage>

    @POST("/api/chat/sendMessage")
    suspend fun sendChatMessage(@Body chatMessage: ChatMessage)
}