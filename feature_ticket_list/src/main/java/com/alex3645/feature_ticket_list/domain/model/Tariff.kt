package com.alex3645.feature_ticket_list.domain.model

data class Tariff(
    val conferenceId: Int,
    val cost: Double,
    val id: Int,
    val name: String,
    val ticketsLeft: Int,
    val ticketsTotal: Int
)