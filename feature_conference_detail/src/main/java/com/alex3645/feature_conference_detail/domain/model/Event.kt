package com.alex3645.feature_conference_detail.domain.model

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_detail.presentation.eventRecyclerView.recyclerView.EventCalendar
import java.util.*

data class Event(
    val conferenceId: Int,
    val dateEnd: String,
    val dateStart: String,
    val description: String,
    val events: List<Event>,
    val id: Int,
    val name: String,
    val speakerId: Int
    ){
    internal fun toEventCalendar(): EventCalendar {
        val startDate: Calendar = Calendar.getInstance(Locale.getDefault())
        val endDate: Calendar = Calendar.getInstance(Locale.getDefault())

        endDate.time = ServerConstants.serverDateTimeFormat.parse(this.dateEnd)
        startDate.time = ServerConstants.serverDateTimeFormat.parse(this.dateStart)
        return EventCalendar(
            conferenceId = this.conferenceId,
            dateEnd = endDate,
            dateStart = startDate,
            description = this.description,
            events = this.events,
            id = this.id,
            name = this.name,
            speakerId = this.speakerId
        )
    }
}