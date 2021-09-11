package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_event_list.databinding.FragmentRecyclerListItemBinding

class ConferenceAdapter : RecyclerView.Adapter<ConferenceAdapter.ViewHolder>() {

    var conferences: List<Conference> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((conference: Conference) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentRecyclerListItemBinding = FragmentRecyclerListItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = conferences.size

    fun setOnDebouncedClickListener(listener: (conference: Conference) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(private val binding: FragmentRecyclerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val card = binding.fragmentRecyclerListItem
        val name = binding.nameTextBox
        val info = binding.shortInfoTextBox

        fun bind(conference: Conference) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(conference) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("2222", "kek")

        var conference = conferences[position]

        holder.name.text = conference.name
        holder.info.text = conference.description

        holder.bind(conference)
    }
}