package com.alex3645.feature_conference_builder.presentation.eventEditorListView.recyclerView

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_builder.domain.model.Event
import java.util.*

data class EventCalendar(
    val conferenceId: Int,
    val dateEnd: Calendar,
    val dateStart: Calendar,
    val description: String,
    val events: List<Event>,
    val id: Int,
    val name: String,
    val speakerId: Int,
    val speakerLogin: String?
){
    internal fun toEvent() : Event{
        return Event(
            conferenceId = this.conferenceId,
            dateEnd = ServerConstants.serverDateTimeFormat.format(this.dateEnd.time),
            dateStart = ServerConstants.serverDateTimeFormat.format(this.dateStart.time),
            description = this.description,
            events = this.events.toMutableList(),
            id = this.id,
            name = this.name,
            speakerId = this.speakerId,
            speakerLogin = this.speakerLogin
        )
    }
}
