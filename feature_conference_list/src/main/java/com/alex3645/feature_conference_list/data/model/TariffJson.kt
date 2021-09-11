package com.alex3645.feature_conference_list.data.model


import com.alex3645.feature_conference_list.domain.model.Tariff
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TariffJson(
    @Json(name = "conference_id")
    val conferenceId: Int,
    @Json(name = "cost")
    val cost: Double,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "tickets_left")
    val ticketsLeft: Int,
    @Json(name = "tickets_total")
    val ticketsTotal: Int
){
    internal fun toDomainModel(): Tariff{
        return Tariff(
            conferenceId = this.conferenceId,
            cost = this.cost,
            id = this.id,
            name = this.name,
            ticketsLeft = this.ticketsLeft,
            ticketsTotal = this.ticketsTotal
        )
    }
}