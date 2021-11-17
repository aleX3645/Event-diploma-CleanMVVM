package com.alex3645.feature_conference_list.data.model


import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_list.domain.model.Conference
import com.google.gson.annotations.SerializedName

data class ConferenceJson(
    @SerializedName("category")
    val category: Int = -1,
    @SerializedName("date_end")
    val dateEnd: String = ServerConstants.serverDateTimeFormat.format(0),
    @SerializedName("date_start")
    val dateStart: String = ServerConstants.serverDateTimeFormat.format(0),
    @SerializedName("description")
    val description: String = "",
    @SerializedName("events")
    val eventsJson: ArrayList<EventJson> = ArrayList(),
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("is_cancelled")
    val isCancelled: Boolean = false,
    @SerializedName("location")
    val location: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("organizer_id")
    val organizerId: Int = -1,
    @SerializedName("status")
    val status: Int = -1,
    @SerializedName("tariffs")
    val tariffsJson: ArrayList<TariffJson> = ArrayList()
){
    internal fun toDomainModel() : Conference{
        return  Conference(
            category = this.category,
            dateEnd = this.dateEnd,
            dateStart = this.dateStart,
            description = this.description,
            events = this.eventsJson.map{it.toDomainModel()},
            id = this.id,
            isCancelled = this.isCancelled,
            location = this.location,
            name = this.name,
            organizerId = this.organizerId,
            status = this.status,
            tariffs = this.tariffsJson.map { it.toDomainModel() }
        )
    }
}