package com.alex3645.feature_search.data.model


import com.alex3645.feature_search.domain.data.Tariff
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
            conferenceId = this.conferenceId,
            cost = this.cost,
            id = this.id,
            name = this.name,
            ticketsLeft = this.ticketsLeft,
            ticketsTotal = this.ticketsTotal
        )
    }
}