package com.alex3645.feature_conference_builder.data.internet.service

import com.alex3645.feature_conference_builder.data.model.ConferenceJson
import com.alex3645.feature_conference_builder.data.model.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiRetrofitBuilderService {
    @POST("api/org/createConference")
    suspend fun addConference(@Header("token") token: String, @Body conference: ConferenceJson) : Response
}