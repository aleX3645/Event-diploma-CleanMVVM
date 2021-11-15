package com.alex3645.feature_account.presentation.settingsView.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.example.feature_account.databinding.SettingsItemBinding

@SuppressLint("NotifyDataSetChanged")
class SettingsAdapter : RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {

    var settingsList: List<String> by observer(listOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((name: String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: SettingsItemBinding = SettingsItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = settingsList.size

    fun setOnDebouncedClickListener(listener: (name: String) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: SettingsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.simpleNameTextBox

        fun bind(name: String) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(name) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = settingsList[position]

        holder.name.text = name

        holder.bind(name)
    }
}