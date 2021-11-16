package com.alex3645.feature_conference_detail.data.model

import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("id")
    val id: Long,
    @SerializedName("conference_id")
    val conferenceId: Long,
    @SerializedName("sender_name")
    val senderName: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("date_time")
    val dateTime: String
)