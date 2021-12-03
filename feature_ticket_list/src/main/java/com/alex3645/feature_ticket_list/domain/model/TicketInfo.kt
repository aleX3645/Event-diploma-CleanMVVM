package com.alex3645.feature_ticket_list.domain.model

data class TicketInfo(
    val conference:Conference,
    val ticket: Ticket,
    val tariff: Tariff
)
