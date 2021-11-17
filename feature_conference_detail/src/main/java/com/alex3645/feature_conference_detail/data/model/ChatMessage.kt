package com.alex3645.feature_conference_detail.data.model

import com.alex3645.app.data.api.ServerConstants
import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("conference_id")
    val conferenceId: Long = -1,
    @SerializedName("sender_name")
    val senderName: String = "",
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date_time")
    val dateTime: String = ServerConstants.serverDateTimeFormat.format(0)
)