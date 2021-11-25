package com.alex3645.feature_conference_detail.presentation.conferenceChatView.chatView;

import android.view.View;

import com.alex3645.feature_conference_detail.R;
import com.alex3645.feature_conference_detail.data.model.ChatMessage;
import com.stfalcon.chatkit.messages.MessageHolders;

public class CustomIncomingTextMessageViewHolder
        extends MessageHolders.IncomingTextMessageViewHolder<ChatMessage> {

    public CustomIncomingTextMessageViewHolder(View itemView, Object payload) {
        super(itemView, payload);
    }

    @Override
    public void onBind(ChatMessage message) {
        super.onBind(message);



        //time.setText(message.);
    }

    public static class Payload {
        public OnAvatarClickListener avatarClickListener;
    }

    public interface OnAvatarClickListener {
        void onAvatarClick();
    }
}
