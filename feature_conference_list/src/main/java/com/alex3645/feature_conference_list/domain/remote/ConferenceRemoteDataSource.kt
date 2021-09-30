package com.alex3645.feature_conference_list.domain.remote

import android.util.Log
import com.alex3645.base.data.BaseDataSource
import com.alex3645.feature_conference_list.data.model.AccResponse
import com.alex3645.feature_conference_list.data.model.AuthRequest
import com.alex3645.feature_conference_list.data.model.ConferenceJson
import com.alex3645.feature_conference_list.data.model.UserRegJson
import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.domain.model.User
import retrofit2.http.Body
import javax.inject.Inject

class ConferenceRemoteDataSource @Inject constructor(
    private val conferenceService: ApiRetrofitConferenceService
): BaseDataSource() {
    suspend fun getConferences(pageSize: Int, pageNumber: Int): List<Conference> {
        return conferenceService.getAllConferences(pageSize,pageNumber).map { it.toDomainModel() }
    }

    suspend fun auth(authRequest: AuthRequest) : AccResponse {
        return conferenceService.auth(authRequest)
    }

    suspend fun register(userReg: UserRegJson) : AccResponse{
        return conferenceService.regAsUser(userReg)
    }

    suspend fun searchConferences(text: String): List<Conference>{
        return conferenceService.searchConferences(text).map { it.toDomainModel() }
    }

    suspend fun searchEvents(text: String): List<Event>{
        return conferenceService.searchEvents(text).map { it.toDomainModel() }
    }

    suspend fun searchUsers(text: String): List<User>{
        return conferenceService.searchUsers(text).map { it.toDomainModel() }
    }
}