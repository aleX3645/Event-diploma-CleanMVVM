package com.alex3645.feature_personal_schedule.presentation.personalScheduleView.recyclerView

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.base.delegate.observer
import com.alex3645.feature_personal_schedule.databinding.EventRecyclerItemBinding
import com.alex3645.feature_personal_schedule.databinding.TitleEventItemBinding
import com.alex3645.feature_personal_schedule.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*


class EventRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var events: List<Event> by observer(listOf()) {
        listDaysSeparatedAdapter = ListDaysSeparatedAdapter(it)
        notifyDataSetChanged()
    }

    private var listDaysSeparatedAdapter = ListDaysSeparatedAdapter(events)

    private var onDebouncedClickListener: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val bindingEvent: EventRecyclerItemBinding = EventRecyclerItemBinding.inflate(inflater, parent, false)
        val bindingTitle: TitleEventItemBinding = TitleEventItemBinding.inflate(inflater, parent, false)

        return when(viewType){
            (0)-> ViewHolderTitle(bindingTitle)
            (1)-> ViewHolderEvent(bindingEvent)
            else-> ViewHolderEvent(bindingEvent)
        }
    }

    override fun getItemCount(): Int = listDaysSeparatedAdapter.getCount()

    fun setOnDebouncedClickListener(listener: (event: Event) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("!!!", position.toString())
        return when (listDaysSeparatedAdapter.getByPosition(position).type) {
            0 -> ListDaysSeparatedAdapter.titleCode
            1 -> ListDaysSeparatedAdapter.eventCode
            else -> ListDaysSeparatedAdapter.eventCode
        }
    }

    inner class ViewHolderEvent(binding: EventRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val name = binding.simpleNameTextBox
        val info = binding.simpleShortInfoTextBox
        val speaker = binding.speakerName
        val startTime = binding.startTime
        val endTime = binding.endTime

        fun bind(event: Event) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(event) }
        }
    }

    inner class ViewHolderTitle(binding: TitleEventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val text = binding.titleText
    }

    private val simpleDateFormatClientTime = SimpleDateFormat("HH:mm", Locale.getDefault())
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = listDaysSeparatedAdapter.getByPosition(position)

        if(holder is ViewHolderEvent){
            holder.name.text = event.event?.name ?: "no data"
            holder.info.text = event.event?.description ?: "no data"
            holder.speaker.text = event.event?.speakerId.toString()
            holder.startTime.text = simpleDateFormatClientTime.format(ServerConstants.serverDateTimeFormat.parse(event.event?.dateStart ?: ""))
            holder.endTime.text = simpleDateFormatClientTime.format(ServerConstants.serverDateTimeFormat.parse(event.event?.dateEnd ?: ""))

            event.event?.let { holder.bind(it) }
        }

        if(holder is ViewHolderTitle){
            holder.text.text = event.title
        }

    }
}