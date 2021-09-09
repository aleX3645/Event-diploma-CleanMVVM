package com.alex3645.feature_conference_list.domain.model

import com.alex3645.feature_event_list.domain.model.Tariff


data class Conference(
    val category: Int,
    val dateEnd: String,
    val dateStart: String,
    val description: String,
    val event: List<Event>,
    val id: Int,
    val isCancelled: Boolean,
    val location: String,
    val name: String,
    val organizerId: Int,
    val status: Int,
    val tariff: List<Tariff>
)
