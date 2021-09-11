package com.alex3645.feature_conference_list.domain.repository

import com.alex3645.base.data.performGetOperation
import com.alex3645.feature_conference_list.domain.remote.ConferenceRemoteDataSource
import javax.inject.Inject


class ConferenceRepository @Inject constructor(
    private val remoteDataSource: ConferenceRemoteDataSource
) {
    suspend fun getConferences(pageSize: Int, pageNumber: Int) =
        remoteDataSource.getConferences(pageSize,pageNumber)
}