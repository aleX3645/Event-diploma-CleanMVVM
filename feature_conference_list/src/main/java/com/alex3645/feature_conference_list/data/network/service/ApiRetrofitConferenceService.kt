package com.alex3645.feature_conference_list.data.network.service

import com.alex3645.feature_conference_list.data.model.ConferenceJson
import retrofit2.Response
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRetrofitConferenceService {
    @GET("api/usr/getAllConferences/")
    suspend fun getAllConferences(@Query("pageSize") pageSize: Int, @Query("pageNumber") pageNumber: Int): List<ConferenceJson>
}