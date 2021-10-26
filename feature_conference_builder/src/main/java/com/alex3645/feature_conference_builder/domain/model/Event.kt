package com.alex3645.feature_conference_builder.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.alex3645.feature_conference_builder.data.model.EventJson

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
    ) {
    }

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

    companion object CREATOR : Parcelable.Creator<Event> {
        override fun createFromParcel(parcel: Parcel): Event {
            return Event(parcel)
        }

        override fun newArray(size: Int): Array<Event?> {
            return arrayOfNulls(size)
        }
    }
}
