package com.alex3645.feature_search.domain.repository

import com.alex3645.feature_search.domain.data.Conference
import com.alex3645.feature_search.domain.data.Event
import com.alex3645.feature_search.domain.data.User

interface SearchRepository {
    suspend fun searchConferences(text: String): List<Conference>

    suspend fun searchEvents(text: String): List<Event>

    suspend fun searchUsers(text: String): List<User>
}