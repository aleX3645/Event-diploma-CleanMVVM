package com.alex3645.feature_conference_detail.domain.repository

import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.model.Ticket
import com.alex3645.feature_conference_detail.domain.model.User

interface ConferenceDetailRepository {
    suspend fun loadAccountByLogin(login: String) : User
    suspend fun loadConferenceById(id: Int) : Conference
    suspend fun loadEventById(id: Int): Event
    suspend fun loadEventsForConferenceWithId(id: Int): List<Event>
    suspend fun loadEventsForEventWithId(id: Int): List<Event>
    suspend fun loadChatByConferenceId(id: Long) : List<ChatMessage>
    suspend fun loadUserById(id: Int) : User

    suspend fun sendChatMessage(chatMessage: ChatMessage)
    suspend fun registerTicket(ticket: Ticket, id: Long, token: String)
}