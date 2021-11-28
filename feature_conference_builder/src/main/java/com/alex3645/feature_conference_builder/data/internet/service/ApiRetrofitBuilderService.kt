package com.alex3645.feature_conference_builder.data.internet.service

import com.alex3645.feature_conference_builder.data.model.ConferenceJson
import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.data.model.UserJson
import retrofit2.http.*

interface ApiRetrofitBuilderService {
    @POST("api/org/createConference")
    suspend fun addConference(@Header("token") token: String, @Body conference: ConferenceJson) : Response

    @GET("api/usr/searchUsers/{val}")
    suspend fun searchUsers(@Path("val") userName: String) : List<UserJson>

    @GET("api/usr/info/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : UserJson
}