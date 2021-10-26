package com.alex3645.feature_conference_builder.domain.repository

import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference

interface ConferenceBuilderRepository {
    suspend fun addConference(conference: Conference) : Response
}