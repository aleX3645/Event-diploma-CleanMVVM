package com.alex3645.feature_conference_list.data.repositoryImpl

import com.alex3645.feature_conference_list.data.network.service.ApiRetrofitConferenceService
import com.alex3645.feature_conference_list.domain.repository.ConferenceRepository
import javax.inject.Inject

class ConferenceRepositoryImpl @Inject constructor(private val conferenceService: ApiRetrofitConferenceService) : ConferenceRepository{
    override suspend fun getConferences(pageSize: Int, pageNumber: Int) =
        conferenceService.getAllConferences(pageSize,pageNumber).map { it.toDomainModel() }
}