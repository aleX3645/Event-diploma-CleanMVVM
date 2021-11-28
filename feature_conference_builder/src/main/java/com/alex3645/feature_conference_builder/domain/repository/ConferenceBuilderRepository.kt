package com.alex3645.feature_conference_builder.domain.repository

import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.User

interface ConferenceBuilderRepository {
    suspend fun addConference(conference: Conference) : Response
    suspend fun searchUsers(text: String): List<User>
    suspend fun loadAccountByLogin(login: String) : User
}