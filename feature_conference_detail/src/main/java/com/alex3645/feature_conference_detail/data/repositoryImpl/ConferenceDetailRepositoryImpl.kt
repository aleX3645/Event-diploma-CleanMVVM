package com.alex3645.feature_conference_detail.data.repositoryImpl

import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.data.network.service.ApiRetrofitConferenceDetailService
import com.alex3645.feature_conference_detail.domain.model.Conference
import com.alex3645.feature_conference_detail.domain.model.Event
import com.alex3645.feature_conference_detail.domain.model.Ticket
import com.alex3645.feature_conference_detail.domain.model.User
import com.alex3645.feature_conference_detail.domain.repository.ConferenceDetailRepository
import javax.inject.Inject

class ConferenceDetailRepositoryImpl @Inject constructor(private val conferenceDetailService: ApiRetrofitConferenceDetailService): ConferenceDetailRepository{

    override suspend fun loadAccountByLogin(login: String): User {
        return conferenceDetailService.loadUserByLogin(login).toDomainModel()
    }

    override suspend fun loadConferenceById(id: Int): Conference {
        return conferenceDetailService.getConferenceById(id).toDomainModel()
    }

    override suspend fun loadEventById(id: Int): Event {
        return conferenceDetailService.getEventById(id).toDomainModel()
    }

    override suspend fun loadEventsForConferenceWithId(id: Int): List<Event> {
        return conferenceDetailService.getEventsForConferenceWithId(id).map { it.toDomainModel() }
    }

    override suspend fun loadEventsForEventWithId(id: Int): List<Event> {
        return conferenceDetailService.getEventsForEventWithId(id).map { it.toDomainModel() }
    }

    override suspend fun loadChatByConferenceId(id: Long) : List<ChatMessage>{
        return conferenceDetailService.getChatByConferenceId(id)
    }

    override suspend fun sendChatMessage(chatMessage: ChatMessage) {
        conferenceDetailService.sendChatMessage(chatMessage)
    }

    override suspend fun registerTicket(ticket: Ticket, id: Long, token: String) {
        conferenceDetailService.registerTicket(
            token = token,
            id = id,
            ticket = ticket.toJson()
        )
    }
}