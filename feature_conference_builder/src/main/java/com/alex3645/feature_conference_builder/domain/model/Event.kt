package com.alex3645.feature_conference_builder.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.alex3645.app.data.api.ServerConstants
import com.alex3645.feature_conference_builder.data.model.EventJson
import com.alex3645.feature_conference_builder.presentation.eventEditorListView.recyclerView.EventCalendar
import java.util.*

data class Event(
    val id: Int,
    var conferenceId: Int?,
    var dateEnd: String?,
    var dateStart: String?,
    var description: String?,
    val events: MutableList<Event>?,
    var name: String?,
    val speakerId: Int?
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(CREATOR),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    fun toJson() : EventJson {
        return EventJson(
            id = this.id,
            conferenceId = this.conferenceId?: 0,
            dateEnd = this.dateEnd?:"",
            dateStart = this.dateStart?:"",
            description = this.description?:"",
            events = this.events?.map{it.toJson()}?.toMutableList()?: mutableListOf(),
            name = this.name?:"",
            speakerId = this.speakerId?:0
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeValue(conferenceId)
        parcel.writeString(dateEnd)
        parcel.writeString(dateStart)
        parcel.writeString(description)
        parcel.writeTypedList(events)
        parcel.writeString(name)
        parcel.writeValue(speakerId)
    }

    override fun describeContents(): Int {
        return 0
    }

    internal fun toEventCalendar(): EventCalendar {
        val startDate: Calendar = Calendar.getInstance(Locale.getDefault())
        val endDate: Calendar = Calendar.getInstance(Locale.getDefault())

        endDate.time = ServerConstants.serverDateTimeFormat.parse(this.dateEnd)
        startDate.time = ServerConstants.serverDateTimeFormat.parse(this.dateStart)

        return EventCalendar(
            conferenceId = this.conferenceId?:0,
            dateEnd = endDate,
            dateStart = startDate,
            description = this.description?:"",
            events = this.events?:mutableListOf(),
            id = this.id,
            name = this.name?:"",
            speakerId = this.speakerId?:0
        )
    }

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}
