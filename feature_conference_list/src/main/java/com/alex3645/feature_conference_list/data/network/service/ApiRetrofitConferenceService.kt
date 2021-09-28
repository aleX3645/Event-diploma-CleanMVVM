package com.alex3645.feature_conference_list.data.network.service

import com.alex3645.feature_conference_list.data.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiRetrofitConferenceService {
    @GET("api/usr/getAllConferences/")
    suspend fun getAllConferences(@Query("pageSize") pageSize: Int, @Query("pageNumber") pageNumber: Int): List<ConferenceJson>

    @POST("api/login/user/")
    suspend fun auth(@Body authRequest: AuthRequest) : AccResponse

    @POST("api/login/organizer/")
    suspend fun authAsOrganizer(@Body authRequest: AuthRequest) : AccResponse

    @POST("api/reg/user/")
    suspend fun regAsUser(@Body user: UserRegJson) : AccResponse
}