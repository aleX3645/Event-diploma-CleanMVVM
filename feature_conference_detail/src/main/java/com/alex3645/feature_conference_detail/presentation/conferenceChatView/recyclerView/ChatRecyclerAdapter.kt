package com.alex3645.feature_conference_detail.presentation.conferenceChatView.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_detail.data.model.ChatMessage
import com.alex3645.feature_conference_detail.databinding.ChatRecyclerItemBinding

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {

    var messages: MutableList<ChatMessage> by observer(mutableListOf()) {
        notifyDataSetChanged()
    }

    //private var onDebouncedClickListener: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ChatRecyclerItemBinding = ChatRecyclerItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = messages.size
/*
    fun setOnDebouncedClickListener(listener: (event: Event) -> Unit) {
        this.onDebouncedClickListener = listener
    }
*/
    inner class ViewHolder(private val binding: ChatRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val content = binding.contentTextView
        val senderName = binding.senderName
/*
        fun bind(chatMessage: ChatMessage) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(event) }
        }*/
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[position]

        holder.content.text = message.content
        holder.senderName.text = message.senderName

        //holder.bind(event)
    }
}