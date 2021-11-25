package com.alex3645.feature_conference_detail.presentation.conferenceChatView.chatView;

import android.view.View;

import com.alex3645.feature_conference_detail.data.model.ChatMessage;
import com.stfalcon.chatkit.messages.MessageHolders;

public class CustomOutcomingTextMessageViewHolder
        extends MessageHolders.OutcomingTextMessageViewHolder<ChatMessage> {

    public CustomOutcomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(ChatMessage message) {
        super.onBind(message);

        //time.setText(message.getStatus() + " " + time.getText());
    }
}
