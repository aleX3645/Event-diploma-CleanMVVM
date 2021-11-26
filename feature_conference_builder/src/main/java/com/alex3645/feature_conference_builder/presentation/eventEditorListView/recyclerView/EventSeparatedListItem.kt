package com.alex3645.feature_conference_builder.presentation.eventEditorListView.recyclerView

import com.alex3645.feature_conference_builder.domain.model.Event


data class EventSeparatedListItem(
    val type: Int,
    val event: Event?,
    val title: String = "no data",
)
