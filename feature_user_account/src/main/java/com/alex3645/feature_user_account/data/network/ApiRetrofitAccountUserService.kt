package com.alex3645.feature_user_account.data.network

import com.alex3645.feature_user_account.data.model.UserJson
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofitAccountUserService {
    @GET("api/usr/info/byId/{id}")
    suspend fun loadUserById(@Path("id") userId: Int) : UserJson
}