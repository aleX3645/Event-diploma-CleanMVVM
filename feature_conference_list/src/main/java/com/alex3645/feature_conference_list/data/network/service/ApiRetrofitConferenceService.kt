package com.alex3645.feature_conference_list.data.network.service

import com.alex3645.feature_conference_list.data.model.*
import retrofit2.http.*

interface ApiRetrofitConferenceService {
    @GET("api/usr/getAllConferences/")
    suspend fun getAllConferences(@Query("pageSize") pageSize: Int, @Query("pageNumber") pageNumber: Int): List<ConferenceJson>
}