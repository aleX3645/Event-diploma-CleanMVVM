package com.alex3645.feature_search.data.repositoryImpl

import com.alex3645.feature_search.data.network.ApiRetrofitSearchService
import com.alex3645.feature_search.domain.data.Conference
import com.alex3645.feature_search.domain.data.Event
import com.alex3645.feature_search.domain.data.User
import com.alex3645.feature_search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchService: ApiRetrofitSearchService) : SearchRepository{
    override suspend fun searchConferences(text: String): List<Conference>{
        return searchService.searchConferences(text).map { it.toDomainModel() }
    }

    override suspend fun searchEvents(text: String): List<Event>{
        return searchService.searchEvents(text).map { it.toDomainModel() }
    }

    override suspend fun searchUsers(text: String): List<User>{
        return searchService.searchUsers(text).map { it.toDomainModel() }
    }
}