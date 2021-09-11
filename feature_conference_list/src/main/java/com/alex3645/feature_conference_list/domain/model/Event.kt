package com.alex3645.feature_conference_list.domain.model

import com.squareup.moshi.Json

data class Event(
    val conferenceId: Int,
    val dateEnd: String,
    val dateStart: String,
    val description: String,
    val events: List<Any>,
    val id: Int,
    val name: String,
    val speakerId: Int
    )
