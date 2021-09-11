package com.alex3645.feature_conference_list.domain.model

import com.alex3645.feature_conference_list.data.model.TariffJson

data class Conference(
    val category: Int,
    val dateEnd: String,
    val dateStart: String,
    val description: String,
    val events: List<Event>,
    val id: Int,
    val isCancelled: Boolean,
    val location: String,
    val name: String,
    val organizerId: Int,
    val status: Int,
    val tariffs: List<Tariff>
    )
