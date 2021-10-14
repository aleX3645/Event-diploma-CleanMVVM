package com.alex3645.feature_conference_builder.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.util.ArrayList

data class Conference(
    val id: Int,
    var category: Int,
    var dateEnd: String?,
    var dateStart: String?,
    var description: String?,
    val events: MutableList<Event>?,
    val isCancelled: Boolean,
    var location: String?,
    var name: String?,
    var organizerId: Int,
    val status: Int,
    val tariffs: List<Tariff>?
    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Event),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.createTypedArrayList(Tariff)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(category)
        parcel.writeString(dateEnd)
        parcel.writeString(dateStart)
        parcel.writeString(description)
        parcel.writeTypedList(events)
        parcel.writeByte(if (isCancelled) 1 else 0)
        parcel.writeString(location)
        parcel.writeString(name)
        parcel.writeInt(organizerId)
        parcel.writeInt(status)
        parcel.writeTypedList(tariffs)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Conference> {
        override fun createFromParcel(parcel: Parcel): Conference {
            return Conference(parcel)
        }

        override fun newArray(size: Int): Array<Conference?> {
            return arrayOfNulls(size)
        }
    }
}
