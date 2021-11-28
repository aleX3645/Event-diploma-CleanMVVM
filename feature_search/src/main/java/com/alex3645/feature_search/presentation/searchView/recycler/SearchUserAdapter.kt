package com.alex3645.feature_search.presentation.searchView.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_search.databinding.SearchSimpleItemBinding
import com.alex3645.feature_search.databinding.SearchUserItemBinding
import com.alex3645.feature_search.domain.data.User
import com.squareup.picasso.Picasso

@SuppressLint("NotifyDataSetChanged")
class SearchUserAdapter : RecyclerView.Adapter<SearchUserAdapter.ViewHolder>(){

    var users: List<User> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((user: User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SearchUserItemBinding = SearchUserItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.size

    fun setOnDebouncedClickListener(listener: (user: User) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: SearchUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.userName
        val info = binding.userInfo
        val userPicture = binding.userImage

        fun bind(user: User) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(user) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]

        holder.name.text = "${user.name} ${user.surname}"
        holder.info.text = user.login

        //Picasso.get().load(user.photoUrl).centerCrop().fit().into(holder.userPicture)

        holder.bind(user)
    }
}