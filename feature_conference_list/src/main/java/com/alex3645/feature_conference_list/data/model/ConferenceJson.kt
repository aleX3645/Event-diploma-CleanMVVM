package com.alex3645.feature_conference_list.data.model


import com.alex3645.feature_conference_list.domain.model.Conference
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConferenceJson(
    @Json(name = "category")
    val category: Int,
    @Json(name = "date_end")
    val dateEnd: String,
    @Json(name = "date_start")
    val dateStart: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "events")
    val eventJson: List<EventJson>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "is_cancelled")
    val isCancelled: Boolean,
    @Json(name = "location")
    val location: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "organizer_id")
    val organizerId: Int,
    @Json(name = "status")
    val status: Int,
    @Json(name = "tariffs")
    val tariffJson: List<TariffJson>
){
    fun toDomainModel() =
        Conference(category = this.category, dateEnd = this.dateEnd, dateStart = this.dateStart, description = this.description,
        event = this.eventJson.map { it.toDomainModel() }, id = this.id, isCancelled = this.isCancelled, location = this.location, name = this.name, organizerId = this.organizerId, status = this.status,
        tariff = this.tariffJson.map { it.toDomainModel() })
}