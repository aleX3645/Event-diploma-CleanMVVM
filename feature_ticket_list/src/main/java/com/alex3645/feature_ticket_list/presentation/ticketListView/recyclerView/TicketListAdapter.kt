package com.alex3645.feature_ticket_list.presentation.ticketListView.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_ticket_list.domain.model.Ticket
import com.alex3645.feature_ticket_list.databinding.TicketItemBinding

class TicketListAdapter : RecyclerView.Adapter<TicketListAdapter.ViewHolder>() {

    var tickets: List<Ticket> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((ticket: Ticket) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TicketItemBinding = TicketItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tickets.size

    fun setOnDebouncedClickListener(listener: (ticket: Ticket) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: TicketItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.nameTextBox

        fun bind(tariff: Ticket) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(tariff) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = tickets[position]

        holder.name.text = ticket.tariffId.toString()

        holder.bind(ticket)
    }
}