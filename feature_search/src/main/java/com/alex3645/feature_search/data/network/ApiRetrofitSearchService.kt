package com.alex3645.feature_search.data.network

import com.alex3645.feature_search.data.model.ConferenceJson
import com.alex3645.feature_search.data.model.EventJson
import com.alex3645.feature_search.data.model.UserJson
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRetrofitSearchService {
    @GET("api/usr/searchConferences/{val}")
    suspend fun searchConferences(@Path("val") conferenceName: String) : List<ConferenceJson>

    @GET("api/usr/searchEvents/{val}")
    suspend fun searchEvents(@Path("val") eventName: String) : List<EventJson>

    @GET("api/usr/searchUsers/{val}")
    suspend fun searchUsers(@Path("val") userName: String) : List<UserJson>
}

