package com.alex3645.feature_conference_list.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SortJson(
    @Json(name = "empty")
    val empty: Boolean,
    @Json(name = "sorted")
    val sorted: Boolean,
    @Json(name = "unsorted")
    val unsorted: Boolean
)