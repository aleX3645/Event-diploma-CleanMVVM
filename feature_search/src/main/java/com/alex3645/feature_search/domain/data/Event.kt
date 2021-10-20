package com.alex3645.feature_search.domain.data

data class Event(
    val conferenceId: Int?,
    val dateEnd: String?,
    val dateStart: String?,
    val description: String?,
    val events: List<Event>?,
    val id: Int?,
    val name: String?,
    val speakerId: Int?
    )