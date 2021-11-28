package com.alex3645.feature_conference_detail.presentation.conferenceChatView.chatView

import android.view.View
import android.widget.TextView
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.stfalcon.chatkit.messages.MessageHolders.OutcomingTextMessageViewHolder
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageOutcomingViewHolder(itemView: View?, payload: Any?) :
    OutcomingTextMessageViewHolder<ChatMessage>(itemView, payload) {

    private val beautyTimeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    override fun onBind(message: ChatMessage) {
        super.onBind(message)
        itemView.findViewById<TextView>(R.id.messageTimeOut).text = beautyTimeFormatter.format(message.createdAt)
    }
}