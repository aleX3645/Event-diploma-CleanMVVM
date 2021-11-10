package com.alex3645.feature_personal_schedule.presentation.personalScheduleView.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_personal_schedule.databinding.EventRecyclerItemBinding
import com.alex3645.feature_personal_schedule.domain.model.Event

class EventRecyclerAdapter : RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder>() {

    var events: List<Event> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: EventRecyclerItemBinding = EventRecyclerItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    fun setOnDebouncedClickListener(listener: (event: Event) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(private val binding: EventRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.simpleNameTextBox
        val info = binding.simpleShortInfoTextBox

        fun bind(event: Event) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(event) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]

        holder.name.text = event.name
        holder.info.text = event.description

        holder.bind(event)
    }
}