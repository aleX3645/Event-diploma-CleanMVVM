package com.alex3645.feature_conference_detail.domain.repository

import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Event

interface ConferenceDetailRepository {
    suspend fun loadConferenceById(id: Int) : Conference
    suspend fun loadEventById(id: Int): Event
    suspend fun loadEventsForConferenceWithId(id: Int): List<Event>
    suspend fun loadChatByConferenceId(id: Long) : List<ChatMessage>
    suspend fun sendChatMessage(chatMessage: ChatMessage)
}