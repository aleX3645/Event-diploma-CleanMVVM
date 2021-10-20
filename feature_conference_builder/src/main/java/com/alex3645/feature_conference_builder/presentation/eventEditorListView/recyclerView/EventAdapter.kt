package com.alex3645.feature_conference_builder.presentation.eventEditorListView.recyclerView


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_builder.databinding.FragmentEventEditorItemBinding
import com.alex3645.feature_conference_builder.domain.model.Event
import java.text.SimpleDateFormat
import java.util.*

class EventAdapter : RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    var events: MutableList<Event> by observer(mutableListOf()) {
        sortEvents()
        notifyDataSetChanged()
    }

    fun sortEvents(){
        events.sortWith { o1: Event, o2: Event -> compFunc(o1, o2) }
    }

    private val simpleDateFormatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sss'Z'", Locale.getDefault())
    private fun compFunc(o1: Event, o2: Event) : Int{
        if(simpleDateFormatServer.parse(o1.dateStart).time==simpleDateFormatServer.parse(o2.dateStart).time){
            return if(simpleDateFormatServer.parse(o1.dateEnd).time>simpleDateFormatServer.parse(o2.dateStart).time){
                1
            }else{
                -1
            }
        }

        return if(simpleDateFormatServer.parse(o1.dateStart).time>simpleDateFormatServer.parse(o2.dateStart).time){
            1
        }else{
            -1
        }
    }

    private var onDebouncedClickListener: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentEventEditorItemBinding = FragmentEventEditorItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = events.size

    fun setOnDebouncedClickListener(listener: (event: Event) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: FragmentEventEditorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.nameTextBox
        val info = binding.shortInfoTextBox

        fun bind(conference: Event) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(conference) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val event = events[position]

        holder.name.text = event.name
        holder.info.text = event.description

        holder.bind(event)
    }
}