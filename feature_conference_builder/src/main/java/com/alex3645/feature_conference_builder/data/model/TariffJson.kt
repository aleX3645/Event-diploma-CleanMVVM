package com.alex3645.feature_conference_builder.data.model


import com.alex3645.feature_conference_builder.domain.model.Tariff
import com.google.gson.annotations.SerializedName

data class TariffJson(
    @SerializedName("conference_id")
    val conferenceId: Int,
    @SerializedName("cost")
    val cost: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("tickets_left")
    val ticketsLeft: Int,
    @SerializedName("tickets_total")
    val ticketsTotal: Int
){
    internal fun toDomainModel(): Tariff {
        return Tariff(
            id = this.id,
            conferenceId = this.conferenceId,
            cost = this.cost,
            name = this.name,
            ticketsLeft = this.ticketsLeft,
            ticketsTotal = this.ticketsTotal
        )
    }
}