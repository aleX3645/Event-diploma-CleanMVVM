package com.alex3645.feature_conference_detail.data.model


import com.alex3645.feature_conference_detail.domain.model.Tariff
import com.google.gson.annotations.SerializedName

data class TariffJson(
    @SerializedName("conference_id")
    val conferenceId: Int = -1,
    @SerializedName("cost")
    val cost: Double = 0.0,
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("tickets_left")
    val ticketsLeft: Int = 0,
    @SerializedName("tickets_total")
    val ticketsTotal: Int = 0
){
    internal fun toDomainModel(): Tariff {
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