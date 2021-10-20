package com.alex3645.feature_search.presentation.searchView.recycler


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_search.databinding.SearchSimpleItemBinding
import com.alex3645.feature_search.domain.data.Conference

class SearchConferenceAdapter : RecyclerView.Adapter<SearchConferenceAdapter.ViewHolder>() {

    var conferences: List<Conference> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((conference: Conference) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SearchSimpleItemBinding = SearchSimpleItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = conferences.size

    fun setOnDebouncedClickListener(listener: (conference: Conference) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: SearchSimpleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.simpleNameTextBox
        val info = binding.simpleShortInfoTextBox

        fun bind(conference: Conference) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(conference) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val conference = conferences[position]

        holder.name.text = conference.name
        holder.info.text = conference.description

        holder.bind(conference)
    }
}