package com.alex3645.feature_account.data.network.service

import com.alex3645.feature_account.data.model.AccResponse
import com.alex3645.feature_account.data.model.UserJson
import com.alex3645.feature_account.domain.model.User
import okhttp3.MultipartBody
import retrofit2.http.*
import java.io.File

interface ApiRetrofitAccountService {
    @GET("api/usr/info/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : UserJson

    @POST("api/usr/change/{login}")
    suspend fun editAccount(@Header("token") token: String, @Body user: UserJson, @Path("login") login: String) : AccResponse

    @Multipart
    @POST("api/usr/upload/picture")
    suspend fun addImage(@Header("token") token: String, @Part file: MultipartBody.Part) : AccResponse
}