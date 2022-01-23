package com.alex3645.feature_conference_builder.domain.repository

import com.alex3645.feature_conference_builder.data.model.ConferenceJson
import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.User
import okhttp3.MultipartBody

interface ConferenceBuilderRepository {
    suspend fun addConference(conference: Conference) : Response
    suspend fun searchUsers(text: String): List<User>
    suspend fun loadAccountByLogin(login: String) : User
    suspend fun changeConference(token: String, id: Int, conferenceJson: ConferenceJson) : Response
    suspend fun loadConferenceById(id: Int) : Conference
    suspend fun uploadImage(token: String, file: MultipartBody.Part) : Response
}