package com.alex3645.feature_conference_list.presentation.conferenceRecyclerView.recyclerView


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.alex3645.base.delegate.observer
import com.alex3645.feature_conference_list.domain.model.Conference
import com.alex3645.feature_conference_list.usecase.LoadPictureByUrlUseCase
import com.alex3645.feature_event_list.databinding.FragmentRecyclerListItemBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@SuppressLint("NotifyDataSetChanged")
class ConferenceAdapter @Inject constructor(val loadPictureByUrlUseCase: LoadPictureByUrlUseCase): RecyclerView.Adapter<ConferenceAdapter.ViewHolder>() {

    var conferences: List<Conference> by observer(listOf()) {
        rawConferences = conferences
        notifyDataSetChanged()
    }

    private var rawConferences: List<Conference> = listOf()

    fun filter(filter: MutableList<Int>, city: String){
        if(filter.size != 0){
            conferences = rawConferences.filter {
                filter.contains(it.category)
            }
        }

        if(city != ""){
            conferences = conferences.filter{
                it.location.contains(city)
            }
        }

    }

    private var onDebouncedClickListener: ((conference: Conference) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: FragmentRecyclerListItemBinding = FragmentRecyclerListItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = conferences.size

    fun setOnDebouncedClickListener(listener: (conference: Conference) -> Unit) {
        this.onDebouncedClickListener = listener
    }

    inner class ViewHolder(binding: FragmentRecyclerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val name = binding.nameTextBox
        val info = binding.shortInfoTextBox
        val image = binding.imageConferenceList

        fun bind(conference: Conference) {
            itemView.setOnClickListener { onDebouncedClickListener?.invoke(conference) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Log.d("2222", "kek")

        val conference = conferences[position]

        holder.name.text = conference.name
        holder.info.text = conference.description

        loadImage(conference.photoUrl,holder.image)

        holder.bind(conference)
    }

    private fun loadImage(url: String, imageView: ImageView) = runBlocking {
        launch {
            loadPictureByUrlUseCase(url,imageView)
        }
    }
}