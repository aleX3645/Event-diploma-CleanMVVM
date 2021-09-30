package com.alex3645.feature_conference_list.presentation.searchView.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_list.domain.model.Event
import com.alex3645.feature_conference_list.domain.model.User
import com.alex3645.feature_event_list.databinding.SearchSimpleItemBinding

class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>(){

    var users: List<User> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((user: User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SearchSimpleItemBinding = SearchSimpleItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    fun setOnDebouncedClickListener(listener: (user: User) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(private val binding: SearchSimpleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.simpleNameTextBox
        val info = binding.simpleShortInfoTextBox

        fun bind(user: User) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(user) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        var user = users[position]

        holder.name.text = user.name
        holder.info.text = user.description

        holder.bind(user)
    }
}