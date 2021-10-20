package com.alex3645.feature_conference_detail.data.repositoryImpl

import com.alex3645.feature_conference_detail.data.network.ApiRetrofitConferenceDetailService
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import javax.inject.Inject

class ConferenceDetailRepositoryImpl @Inject constructor(private val conferenceDetailService: ApiRetrofitConferenceDetailService): ConferenceDetailRepository{
    override suspend fun loadConferenceById(id: Int): Conference {
        return conferenceDetailService.getConferenceById(id).toDomainModel()
    }

    override suspend fun loadEventById(id: Int): Event {
        return conferenceDetailService.getEventById(id).toDomainModel()
    }

    override suspend fun loadEventsForConferenceWithId(id: Int): List<Event> {
        return conferenceDetailService.getEventsForConferenceWithId(id).map { it.toDomainModel() }
    }
}