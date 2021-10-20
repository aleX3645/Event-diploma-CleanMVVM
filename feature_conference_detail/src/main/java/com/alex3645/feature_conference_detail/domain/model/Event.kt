package com.alex3645.feature_conference_list.domain.model

import android.os.Parcel
import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize

data class Event(
    val conferenceId: Int?,
    val dateEnd: String?,
    val dateStart: String?,
    val description: String?,
    val events: List<Event>?,
    val id: Int?,
    val name: String?,
    val speakerId: Int?
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(CREATOR),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(conferenceId)
        parcel.writeString(dateEnd)
        parcel.writeString(dateStart)
        parcel.writeString(description)
        parcel.writeTypedList(events)
        parcel.writeValue(id)
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
