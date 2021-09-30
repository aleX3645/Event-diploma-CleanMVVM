package com.alex3645.feature_conference_list.domain.repository

import com.alex3645.base.data.performGetOperation
import com.alex3645.feature_conference_list.data.model.AuthRequest
import com.alex3645.feature_conference_list.data.model.UserRegJson
import com.alex3645.feature_conference_list.domain.remote.ConferenceRemoteDataSource
import retrofit2.http.Body
import javax.inject.Inject


class ConferenceRepository @Inject constructor(
    private val remoteDataSource: ConferenceRemoteDataSource
) {
    suspend fun getConferences(pageSize: Int, pageNumber: Int) =
            remoteDataSource.getConferences(pageSize,pageNumber)

    suspend fun auth(authRequest: AuthRequest) =
            remoteDataSource.auth(authRequest)

    suspend fun register(regUser: UserRegJson) =
            remoteDataSource.register(regUser)

    suspend fun searchConferences(text: String) =
            remoteDataSource.searchConferences(text)

    suspend fun searchEvents(text: String) =
        remoteDataSource.searchEvents(text)

    suspend fun searchUsers(text: String) =
        remoteDataSource.searchUsers(text)
}