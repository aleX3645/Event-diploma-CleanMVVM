package com.alex3645.feature_conference_detail.presentation.eventRecyclerView.recyclerView

import com.alex3645.feature_conference_detail.domain.model.Event
import java.util.*

data class EventSeparatedListItem(
    val type: Int,
    val event: Event?,
    val title: String = "no data",
)
