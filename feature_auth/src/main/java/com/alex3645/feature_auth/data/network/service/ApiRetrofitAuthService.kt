package com.alex3645.feature_auth.data.network.service

import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.data.model.UserRegJson
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRetrofitAuthService {
    @POST("api/login/user/")
    suspend fun authAsUser(@Body authRequest: AuthRequest) : AccResponse

    @POST("api/login/organizer/")
    suspend fun authAsOrganizer(@Body authRequest: AuthRequest) : AccResponse

    @POST("api/reg/user/")
    suspend fun regAsUser(@Body user: UserRegJson) : AccResponse

    @POST("api/reg/organizer/")
    suspend fun regAsOrganizer(@Body user: UserRegJson) : AccResponse
}