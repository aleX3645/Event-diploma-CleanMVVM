package com.alex3645.feature_conference_list.domain.remote

import com.alex3645.base.data.BaseDataSource
import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.domain.model.User
import javax.inject.Inject

class ConferenceRemoteDataSource @Inject constructor(
    private val conferenceService: ApiRetrofitConferenceService
): BaseDataSource() {
    suspend fun getConferences(pageSize: Int, pageNumber: Int): List<Conference> {
        return conferenceService.getAllConferences(pageSize,pageNumber).map { it.toDomainModel() }
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