package com.alex3645.feature_ticket_list.domain.model

import com.alex3645.feature_ticket_list.data.model.TicketJson


data class Ticket(
    var buyerId: Long,
    var id: Long,
    var released: String,
    var tariffId: Long
){
    internal fun toJson() : TicketJson {
        return TicketJson(
            id = this.id,
            buyerId = this.buyerId,
            released = this.released,
            tariffId = this.tariffId
        )
    }
}
