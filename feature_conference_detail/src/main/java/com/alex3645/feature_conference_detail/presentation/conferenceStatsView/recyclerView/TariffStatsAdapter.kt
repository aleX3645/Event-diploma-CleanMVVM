package com.alex3645.feature_conference_detail.presentation.conferenceStatsView.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_detail.databinding.StatisticaTariffItemBinding
import com.alex3645.feature_conference_detail.databinding.TariffItemBinding
import com.alex3645.feature_conference_detail.domain.model.Tariff
import com.alex3645.feature_conference_detail.presentation.tariffListView.recyclerView.TariffAdapter

class TariffStatsAdapter : RecyclerView.Adapter<TariffStatsAdapter.ViewHolder>() {

    var tariffs: MutableList<Tariff> by observer(mutableListOf()) {
        notifyDataSetChanged()
    }

    private var onDebouncedClickListener: ((tariff: Tariff) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: StatisticaTariffItemBinding = StatisticaTariffItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tariffs.size

    fun setOnDebouncedClickListener(listener: (tariff: Tariff) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: StatisticaTariffItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.tariffName
        val tariffCounter = binding.tariffCounter
        val progressBar = binding.tariffProgressBar

        fun bind(tariff: Tariff) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(tariff) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tariff = tariffs[position]

        holder.name.text = tariff.name
        holder.tariffCounter.text = "${tariff.ticketsLeft}/${tariff.ticketsTotal}"
        holder.progressBar.progress = ((tariff.ticketsLeft.toDouble()/tariff.ticketsTotal.toDouble())*100).toInt()

        holder.bind(tariff)
    }
}