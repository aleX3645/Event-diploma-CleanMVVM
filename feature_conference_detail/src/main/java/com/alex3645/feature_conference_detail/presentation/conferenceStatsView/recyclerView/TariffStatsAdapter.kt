package com.alex3645.feature_conference_detail.presentation.conferenceStatsView.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.ItemTariffHolderBinding
import com.alex3645.feature_conference_detail.databinding.StatisticaTariffItemBinding
import com.alex3645.feature_conference_detail.domain.model.Tariff

@SuppressLint("NotifyDataSetChanged")
class TariffStatsAdapter(val context: Context) : RecyclerView.Adapter<TariffStatsAdapter.TariffHolder>() {

    var tariffs: MutableList<Tariff> by observer(mutableListOf()) {
        freeTariffItems = it.filter{it1-> it1.cost == 0.0}
        paidTariffItems = it.filter{it1-> it1.cost != 0.0}
        notifyDataSetChanged()
    }

    private var paidTariffItems = listOf<Tariff>()
    private var freeTariffItems = listOf<Tariff>()

    private var titles = listOf(context.resources.getString(R.string.registrations),
        context.resources.getString(R.string.tickets))

    private var onDebouncedClickListener: ((tariff: Tariff) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TariffHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemTariffHolderBinding = ItemTariffHolderBinding.inflate(inflater, parent, false)

        return TariffHolder(binding)
    }

    override fun getItemCount(): Int = titles.size

    fun setOnDebouncedClickListener(listener: (tariff: Tariff) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class TariffHolder(binding: ItemTariffHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val linearLayout = binding.holderLinearLayout
        val title = binding.title
        val card = binding.tariffHolderCard
    }

    override fun onBindViewHolder(holder: TariffHolder, position: Int) {

        holder.title.text = titles[position]

        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        when(position){
            0->{
                if(freeTariffItems.isEmpty()){
                    holder.card.visibility = View.GONE
                }else{
                    holder.card.visibility = View.VISIBLE
                }

                for(tariff in freeTariffItems){
                    val tariffItem = layoutInflater.inflate(R.layout.statistica_tariff_item, null)
                    tariffItem.findViewById<TextView>(R.id.tariffName).text = tariff.name
                    tariffItem.findViewById<TextView>(R.id.tariffCounter).text = "${tariff.ticketsLeft} / ${tariff.ticketsTotal}"
                    tariffItem.findViewById<ProgressBar>(R.id.tariffProgressBar).progress =
                        ((tariff.ticketsLeft.toDouble()/tariff.ticketsTotal.toDouble())*100).toInt()

                    holder.linearLayout.addView(tariffItem)
                }
            }
            1->{
                if(paidTariffItems.isEmpty()){
                    holder.card.visibility = View.GONE
                }else{
                    holder.card.visibility = View.VISIBLE
                }

                for(tariff in paidTariffItems){
                    val tariffItem = layoutInflater.inflate(R.layout.statistica_tariff_item, null)
                    tariffItem.findViewById<TextView>(R.id.tariffName).text = tariff.name
                    tariffItem.findViewById<TextView>(R.id.tariffCounter).text = "${tariff.ticketsLeft} / ${tariff.ticketsTotal}"
                    tariffItem.findViewById<ProgressBar>(R.id.tariffProgressBar).progress =
                        ((tariff.ticketsLeft.toDouble()/tariff.ticketsTotal.toDouble())*100).toInt()

                    holder.linearLayout.addView(tariffItem)
                }
            }
        }
    }
}