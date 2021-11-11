package com.alex3645.feature_personal_schedule.data.network.service

import com.alex3645.feature_personal_schedule.data.model.EventJson
import com.alex3645.feature_personal_schedule.data.model.UserJson
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiRetrofitPersonalScheduleService {
    @GET("api/usr/info/{login}")
    suspend fun loadUserByLogin(@Path("login") login: String) : UserJson

    @GET("api/usr/personal/getActualPersonalEvents/{userId}")
    suspend fun getPersonalSchedule(@Path("userId") id: Long, @Header("token") token: String) : List<EventJson>
}