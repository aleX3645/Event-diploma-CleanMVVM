package com.alex3645.feature_conference_builder.data.repositoryImpl

import android.content.Context
import com.alex3645.app.android.SharedPreferencesManager
import com.alex3645.feature_conference_builder.data.internet.service.ApiRetrofitBuilderService
import com.alex3645.feature_conference_builder.data.model.ConferenceJson
import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.model.User
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import javax.inject.Inject

class ConferenceBuilderRepositoryImpl @Inject constructor(
    private val service: ApiRetrofitBuilderService,
    context: Context
    ): ConferenceBuilderRepository {

    private val sharedPreferences = SharedPreferencesManager(context)

    override suspend fun addConference(conference: Conference) : Response{
        return service.addConference(sharedPreferences.fetchAuthToken()?: "",conference.toJson())
    }

    override suspend fun searchUsers(text: String): List<User>{
        return service.searchUsers(text).map { it.toDomainModel() }
    }

    override suspend fun loadAccountByLogin(login: String): User {
        return service.loadUserByLogin(login).toDomainModel()
    }

    override suspend fun changeConference(token: String, id: Int, conferenceJson: ConferenceJson) : Response{
        return service.changeConference(token, id, conferenceJson)
    }

    override suspend fun loadConferenceById(id: Int): Conference {
        return service.getConferenceById(id).toDomainModel()
    }
}