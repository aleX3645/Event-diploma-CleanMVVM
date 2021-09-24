package com.alex3645.feature_conference_list.domain.remote

import android.util.Log
import com.alex3645.base.data.BaseDataSource
import com.alex3645.feature_conference_list.data.model.AuthRequest
import com.alex3645.feature_conference_list.data.model.AuthResponse
import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.domain.model.Conference
import retrofit2.http.Body
import javax.inject.Inject

class ConferenceRemoteDataSource @Inject constructor(
    private val conferenceService: ApiRetrofitConferenceService
): BaseDataSource() {
    suspend fun getConferences(pageSize: Int, pageNumber: Int): List<Conference> {
        return conferenceService.getAllConferences(pageSize,pageNumber).map { it.toDomainModel() }
    }

    suspend fun auth(authRequest: AuthRequest) : AuthResponse{
        return conferenceService.auth(authRequest)
    }
}