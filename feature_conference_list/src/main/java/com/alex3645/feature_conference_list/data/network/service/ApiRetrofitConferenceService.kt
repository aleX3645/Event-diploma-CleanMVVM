package com.alex3645.feature_conference_list.data.network.service

import com.alex3645.feature_conference_list.data.model.ConferenceResponseJsonItem
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRetrofitConferenceService {

    @GET("api/usr/getAllConferences/")
    suspend fun getAllConferences(@Query("pageSize") pageSize: Int, @Query("pageNumber") pageNumber: Int): ArrayList<ConferenceResponseJsonItem>

    companion object Factory {
        fun create(): ApiRetrofitConferenceService {
            val retrofit = retrofit2.Retrofit.Builder()
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl(MainApiServerAddress.LOCAL_SERVER)
                    .build()

            return retrofit.create(ApiRetrofitConferenceService::class.java)
        }
    }
}