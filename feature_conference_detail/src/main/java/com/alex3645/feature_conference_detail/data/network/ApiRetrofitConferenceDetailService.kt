package com.alex3645.feature_conference_detail.data.network

import com.alex3645.feature_conference_detail.data.model.ConferenceJson
import com.alex3645.feature_conference_detail.data.model.EventJson
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofitConferenceDetailService {
    @GET("/api/usr/conference/{id}")
    suspend fun getConferenceById(@Path("id") id: Int): ConferenceJson

    @GET("/api/usr/event/{id}")
    suspend fun getEventById(@Path("id") id: Int): EventJson

    @GET("/api/usr/conferenceEvents/{id}")
    suspend fun getEventsForConferenceWithId(@Path("id") id: Int): List<EventJson>
}