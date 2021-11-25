package com.alex3645.feature_conference_detail.data.model

import android.util.Log
import com.alex3645.app.data.api.ServerConstants
import com.google.gson.annotations.SerializedName
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*

data class ChatMessage(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("conference_id")
    val conferenceId: Long = -1,
    @SerializedName("user")
    val user: UserMessage,
    @SerializedName("content")
    val content: String = "",
    @SerializedName("date_time")
    val dateTime: String = ServerConstants.serverDateTimeFormat.format(0)
) : IMessage{
    override fun getId(): String {
        return user.id.toString()
    }

    override fun getText(): String {
        return content
    }

    override fun getUser(): IUser {
        return user
    }

    override fun getCreatedAt(): Date {
        return ServerConstants.serverDateTimeFormat.parse(dateTime)?:Date()
    }
}