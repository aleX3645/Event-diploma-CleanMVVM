package com.alex3645.feature_conference_builder.data.repositoryImpl

import android.content.Context
import com.alex3645.base.android.SharedPreferencesManager
import com.alex3645.feature_conference_builder.data.internet.service.ApiRetrofitBuilderService
import com.alex3645.feature_conference_builder.data.model.Response
import com.alex3645.feature_conference_builder.domain.model.Conference
import com.alex3645.feature_conference_builder.domain.repository.ConferenceBuilderRepository
import javax.inject.Inject

class ConferenceBuilderRepositoryImpl @Inject constructor(
    private val service: ApiRetrofitBuilderService,
    context: Context
    ): ConferenceBuilderRepository {

    private val sharedPreferences = SharedPreferencesManager(context)

    override suspend fun addConference(conference: Conference) : Response{
        return service.addConference(sharedPreferences.fetchAuthToken()?: "",conference)
    }
}