package com.alex3645.feature_ticket_list.data.model


import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.google.gson.annotations.SerializedName

data class TicketJson(
    @SerializedName("buyer_id")
    var buyerId: Long = -1,
    @SerializedName("id")
    var id: Long = -1,
    @SerializedName("released")
    var released: String = "",
    @SerializedName("tariff_id")
    var tariffId: Long = -1
){
    internal fun toDomainModel() : Ticket {
        return Ticket(
            id = this.id,
            buyerId = this.id,
            released = this.released,
            tariffId = this.tariffId
        )
    }
}