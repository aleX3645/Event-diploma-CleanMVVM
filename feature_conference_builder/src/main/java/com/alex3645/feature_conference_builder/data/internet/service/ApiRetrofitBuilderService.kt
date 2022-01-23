package com.alex3645.feature_conference_builder.data.internet.service

import com.alex3645.feature_conference_builder.data.model.ConferenceJson
import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.data.model.UserJson
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiRetrofitBuilderService {
    @POST("api/org/createConference")
    suspend fun addConference(@Header("token") token: String, @Body conference: ConferenceJson) : Response

    @GET("api/usr/searchUsers/{val}")
    suspend fun searchUsers(@Path("val") userName: String) : List<UserJson>

    @GET("api/usr/info/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : UserJson

    @POST("api/org/changeConferenceWithAllElements/{id}")
    suspend fun changeConference(@Header("token") token: String, @Path("id") id: Int, @Body conferenceJson: ConferenceJson) : Response

    @GET("/api/usr/conferenceWithEvents/{id}")
    suspend fun getConferenceById(@Path("id") id: Int): ConferenceJson

    @Multipart
    @POST("api/usr/upload/picture")
    suspend fun addImage(@Header("token") token: String, @Part file: MultipartBody.Part) : Response
}