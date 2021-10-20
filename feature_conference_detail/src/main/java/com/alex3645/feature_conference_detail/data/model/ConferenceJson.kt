package com.alex3645.feature_conference_detail.data.model


import com.alex3645.feature_conference_detail.domain.model.Conference
import com.google.gson.annotations.SerializedName

data class ConferenceJson(
    @SerializedName("category")
    val category: Int,
    @SerializedName("date_end")
    val dateEnd: String,
    @SerializedName("date_start")
    val dateStart: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("events")
    val eventsJson: ArrayList<EventJson>?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("is_cancelled")
    val isCancelled: Boolean,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("organizer_id")
    val organizerId: Int,
    @SerializedName("status")
    val status: Int,
    @SerializedName("tariffs")
    val tariffsJson: ArrayList<TariffJson>
){
    internal fun toDomainModel() : Conference {
        return  Conference(
            category = this.category,
            dateEnd = this.dateEnd,
            dateStart = this.dateStart,
            description = this.description,
            events = this.eventsJson?.map{it.toDomainModel()},
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