package com.alex3645.feature_conference_list.data.model


import com.alex3645.feature_conference_list.domain.model.Event
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventJson(
    @Json(name = "conference_id")
    val conferenceId: Int,
    @Json(name = "date_end")
    val dateEnd: String,
    @Json(name = "date_start")
    val dateStart: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "events")
    val events: List<Any>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "speaker_id")
    val speakerId: Int
){
    fun toDomainModel() =
        Event(conferenceId = this.conferenceId,
            dateEnd = this.dateEnd,
            dateStart = this.dateStart,
            description = this.description,
            events = this.events,
            id = this.id,
            name = this.name,
            speakerId = this.speakerId)
}