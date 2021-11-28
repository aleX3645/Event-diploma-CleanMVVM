package com.alex3645.feature_conference_builder.data.model


import com.alex3645.feature_conference_builder.domain.model.Event
import com.google.gson.annotations.SerializedName

data class EventJson(
    @SerializedName("conference_id")
    val conferenceId: Int,
    @SerializedName("date_end")
    val dateEnd: String,
    @SerializedName("date_start")
    val dateStart: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("events")
    val events: MutableList<EventJson>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("speaker_id")
    val speakerId: Int?,
    @SerializedName("speaker_login")
    val speakerLogin: String
){
    internal fun toDomainModel(): Event {
        return Event(
            id = this.id,
            conferenceId = this.conferenceId,
            dateEnd = this.dateEnd,
            dateStart = this.dateStart,
            description = this.description,
            events = this.events?.map{it.toDomainModel()}?.toMutableList(),
            name = this.name,
            speakerId = null,
            speakerLogin = this.speakerLogin
        )
    }
}