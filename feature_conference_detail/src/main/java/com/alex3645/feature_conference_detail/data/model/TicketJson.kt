package com.alex3645.feature_conference_detail.data.model


import com.alex3645.feature_conference_detail.domain.model.Ticket
import com.google.gson.annotations.SerializedName

data class TicketJson(
    @SerializedName("buyer_id")
    var buyerId: Long,
    @SerializedName("id")
    var id: Long,
    @SerializedName("released")
    var released: String,
    @SerializedName("tariff_id")
    var tariffId: Long
){
    internal fun toDomainModel() : Ticket{
        return Ticket(
            id = this.id,
            buyerId = this.id,
            released = this.released,
            tariffId = this.tariffId
        )
    }
}