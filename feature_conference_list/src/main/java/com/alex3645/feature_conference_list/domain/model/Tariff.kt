package com.alex3645.feature_event_list.domain.model

import com.squareup.moshi.Json

data class Tariff(
    val conferenceId: Int,
    val cost: Double,
    val id: Int,
    val name: String,
    val ticketsLeft: Int,
    val ticketsTotal: Int
)
