package com.alex3645.feature_conference_list.data.network.service

import com.alex3645.feature_conference_list.data.model.*
import retrofit2.http.*

interface ApiRetrofitConferenceService {
    @GET("api/usr/getAllConferences/")
    suspend fun getAllConferences(@Query("pageSize") pageSize: Int, @Query("pageNumber") pageNumber: Int): List<ConferenceJson>

    @GET("api/usr/searchConferences/{val}")
    suspend fun searchConferences(@Path("val") conferenceName: String) : List<ConferenceJson>

    @GET("api/usr/searchEvents/{val}")
    suspend fun searchEvents(@Path("val") eventName: String) : List<EventJson>

    @GET("api/usr/searchUsers/{val}")
    suspend fun searchUsers(@Path("val") userName: String) : List<UserJson>

}