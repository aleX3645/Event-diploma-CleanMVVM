package com.alex3645.feature_conference_list.domain.repository

import com.alex3645.base.data.performGetOperation
import com.alex3645.feature_conference_list.data.model.AuthRequest
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
}