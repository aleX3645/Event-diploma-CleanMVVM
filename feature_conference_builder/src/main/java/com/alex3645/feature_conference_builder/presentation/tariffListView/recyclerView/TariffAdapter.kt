package com.alex3645.feature_conference_builder.presentation.tariffListView.recyclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_builder.databinding.TariffItemBinding
import com.alex3645.feature_conference_builder.domain.model.Tariff

@SuppressLint("NotifyDataSetChanged")
class TariffAdapter : RecyclerView.Adapter<TariffAdapter.ViewHolder>() {

    var tariffs: MutableList<Tariff> by observer(mutableListOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((tariff: Tariff) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: TariffItemBinding = TariffItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tariffs.size

    inner class ViewHolder(binding: TariffItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.nameTextBox
        val tickets = binding.ticketsTotal
        val cost = binding.cost

        fun bind(tariff: Tariff) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(tariff) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tariff = tariffs[position]

        holder.name.text = tariff.name
        holder.tickets.text = tariff.ticketsTotal.toString()
        holder.cost.text = tariff.cost.toString()

        holder.bind(tariff)
    }
}