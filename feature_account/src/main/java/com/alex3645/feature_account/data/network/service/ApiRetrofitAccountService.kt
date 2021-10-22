package com.alex3645.feature_account.data.network.service

import com.alex3645.feature_account.data.model.AccResponse
import com.alex3645.feature_account.data.model.UserJson
import com.alex3645.feature_account.domain.model.User
import retrofit2.http.*

interface ApiRetrofitAccountService {
    @GET("api/usr/info/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : UserJson

    @POST("api/usr/change/{login}")
    suspend fun editAccount(@Header("token") token: String, @Body user: User, @Path("login") login: String) : AccResponse
}