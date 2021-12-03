package com.alex3645.feature_ticket_list.presentation.ticketListView.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.delegate.observer
import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.databinding.TicketItemBinding
import com.alex3645.feature_ticket_list.domain.model.TicketInfo
import java.text.SimpleDateFormat
import java.util.*

class TicketListAdapter : RecyclerView.Adapter<TicketListAdapter.ViewHolder>() {

    var tickets: List<TicketInfo> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((ticket: TicketInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TicketItemBinding = TicketItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tickets.size

    fun setOnDebouncedClickListener(listener: (ticket: TicketInfo) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: TicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.mainNameTextView
        val supName = binding.supNameTextView
        val startDate = binding.startDate

        fun bind(ticket: TicketInfo) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(ticket) }
        }
    }

    private val beautyDateTimeFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]

        holder.name.text = ticket.conference.name
        holder.supName.text = ticket.tariff.name
        holder.startDate.text = beautyDateTimeFormatter.format(ServerConstants.serverDateTimeFormat.parse(ticket.conference.dateStart))

        holder.bind(ticket)
    }
}