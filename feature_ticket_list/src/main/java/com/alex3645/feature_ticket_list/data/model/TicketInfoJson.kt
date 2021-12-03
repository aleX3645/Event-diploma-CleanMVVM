package com.alex3645.feature_ticket_list.data.model

import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import com.google.gson.annotations.SerializedName

data class TicketInfoJson(
    @SerializedName("conference")
    val conferenceJson: ConferenceJson,
    @SerializedName("tariff")
    val tariffJson: TariffJson,
    @SerializedName("ticket")
    val ticketJson: TicketJson,
){
    fun toDomainModel() : TicketInfo{
        return TicketInfo(
            conference = conferenceJson.toDomainModel(),
            tariff = tariffJson.toDomainModel(),
            ticket = ticketJson.toDomainModel()
        )
    }

}
