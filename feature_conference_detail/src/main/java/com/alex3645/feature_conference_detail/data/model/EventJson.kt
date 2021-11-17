package com.alex3645.feature_conference_detail.data.model


import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_detail.domain.model.Event
import com.google.gson.annotations.SerializedName

data class EventJson(
    @SerializedName("conference_id")
    val conferenceId: Int = -1,
    @SerializedName("date_end")
    val dateEnd: String = ServerConstants.serverDateTimeFormat.format(0),
    @SerializedName("date_start")
    val dateStart: String = ServerConstants.serverDateTimeFormat.format(0),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("events")
    val events: ArrayList<EventJson> = ArrayList(),
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("speaker_id")
    val speakerId: Int = -1
){
    internal fun toDomainModel(): Event {
        return Event(
            conferenceId = this.conferenceId,
            dateEnd = this.dateEnd,
            dateStart = this.dateStart,
            description = this.description,
            events = this.events.map{it.toDomainModel()},
            id = this.id,
            name = this.name,
            speakerId = this.speakerId
        )
    }
}