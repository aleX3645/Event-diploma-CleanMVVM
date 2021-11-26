package com.alex3645.feature_personal_schedule.presentation.personalScheduleView.recyclerView

import com.alex3645.feature_personal_schedule.domain.model.Event

data class EventSeparatedListItem(
    val type: Int,
    val event: Event?,
    val title: String = "no data",
)
