package com.alex3645.feature_conference_detail.presentation.tariffListView.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_detail.R
import com.alex3645.feature_conference_detail.databinding.ItemTariffHolderBinding
import com.alex3645.feature_conference_detail.databinding.TariffItemBinding
import com.alex3645.feature_conference_detail.domain.model.Tariff

@SuppressLint("NotifyDataSetChanged")
class TariffAdapter(private val context: Context) : RecyclerView.Adapter<TariffAdapter.TariffHolder>() {

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
                    val freeTariffItem = layoutInflater.inflate(R.layout.item_tariff_free, null)
                    freeTariffItem.findViewById<TextView>(R.id.freeTariffNameTextView).text = tariff.name
                    freeTariffItem.findViewById<TextView>(R.id.regsTotal).text = "${tariff.ticketsLeft} / ${tariff.ticketsTotal}"

                    if(tariff.ticketsTotal <= tariff.ticketsLeft){
                        freeTariffItem.alpha = .5f
                    }else{
                        freeTariffItem.setOnClickListener { onDebouncedClickListener?.invoke(tariff) }
                    }

                    holder.linearLayout.addView(freeTariffItem)
                }
            }
            1->{
                if(paidTariffItems.isEmpty()){
                    holder.card.visibility = View.GONE
                }else{
                    holder.card.visibility = View.VISIBLE
                }

                for(tariff in paidTariffItems){
                    val paidTariffItem = layoutInflater.inflate(R.layout.tariff_item, null)
                    paidTariffItem.findViewById<TextView>(R.id.paidTariffNameTextView).text = tariff.name
                    paidTariffItem.findViewById<TextView>(R.id.ticketsTotal).text = "${tariff.ticketsLeft} / ${tariff.ticketsTotal}"
                    paidTariffItem.findViewById<TextView>(R.id.cost).text = tariff.cost.toString()

                    if(tariff.ticketsTotal <= tariff.ticketsLeft){
                        paidTariffItem.alpha = .5f
                    }else{
                        paidTariffItem.setOnClickListener { onDebouncedClickListener?.invoke(tariff) }
                    }

                    holder.linearLayout.addView(paidTariffItem)
                }
            }
        }
    }
}