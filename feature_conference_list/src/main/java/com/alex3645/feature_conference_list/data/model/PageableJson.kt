package com.alex3645.feature_conference_list.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PageableJson(
    @Json(name = "offset")
    val offset: Int,
    @Json(name = "pageNumber")
    val pageNumber: Int,
    @Json(name = "pageSize")
    val pageSize: Int,
    @Json(name = "paged")
    val paged: Boolean,
    @Json(name = "sort")
    val sortJson: SortJson,
    @Json(name = "unpaged")
    val unpaged: Boolean
)