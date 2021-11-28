package com.alex3645.feature_conference_detail.presentation.conferenceChatView.chatView

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.stfalcon.chatkit.messages.MessageHolders
import java.text.SimpleDateFormat
import java.util.*

class ChatMessageIncomingViewHolder (itemView: View?, payload: Any?) :
    MessageHolders.IncomingTextMessageViewHolder<ChatMessage>(itemView, payload) {


    private val beautyTimeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    override fun onBind(message: ChatMessage) {
        super.onBind(message)
        itemView.findViewById<TextView>(R.id.senderName).text = message.user.name
        itemView.findViewById<TextView>(R.id.messageTimeIn).text = beautyTimeFormatter.format(message.createdAt)

        payload = Payload()

        (payload as Payload).avatarClickListener = {}

        imageLoader.loadImage(itemView.findViewById(R.id.messageUserAvatar), message.user.photoUrl, payload)
    }

    class Payload {
        var avatarClickListener: (()->Unit)? = null
    }

    interface OnAvatarClickListener {
        fun onAvatarClick()
    }
}