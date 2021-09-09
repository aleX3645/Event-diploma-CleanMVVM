package com.alex3645.feature_conference_list.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConferenceResponseJsonItem(
    @Json(name = "content")
    val conferenceJsonList: List<ConferenceJson>,
    @Json(name = "empty")
    val empty: Boolean,
    @Json(name = "first")
    val first: Boolean,
    @Json(name = "last")
    val last: Boolean,
    @Json(name = "number")
    val number: Int,
    @Json(name = "numberOfElements")
    val numberOfElements: Int,
    @Json(name = "pageable")
    val pageableJson: PageableJson,
    @Json(name = "size")
    val size: Int,
    @Json(name = "sort")
    val sortJson: SortXJson,
    @Json(name = "totalElements")
    val totalElements: Int,
    @Json(name = "totalPages")
    val totalPages: Int
)