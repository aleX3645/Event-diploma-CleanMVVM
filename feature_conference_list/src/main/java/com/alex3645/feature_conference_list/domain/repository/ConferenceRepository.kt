package com.alex3645.feature_conference_list.domain.repository

import com.alex3645.feature_conference_list.domain.model.Conference

interface ConferenceRepository {
    suspend fun getConferences(pageSize: Int, pageNumber: Int) : List<Conference>
}