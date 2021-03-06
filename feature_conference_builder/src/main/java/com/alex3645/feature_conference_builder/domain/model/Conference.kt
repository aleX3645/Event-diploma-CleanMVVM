package com.alex3645.feature_conference_builder.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.alex3645.feature_conference_builder.data.model.ConferenceJson

data class Conference(
    val id: Int,
    var category: Int,
    var dateEnd: String?,
    var dateStart: String?,
    var description: String?,
    var events: MutableList<Event>?,
    val isCancelled: Boolean,
    var location: String?,
    var name: String?,
    var organizerId: Int?,
    val status: Int,
    var tariffs: MutableList<Tariff>?,
    var organizerLogin: String?,
    var photoUrl: String?
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
        parcel.createTypedArrayList(Tariff),
        parcel.readString(),
        parcel.readString()
    )

    fun toJson(): ConferenceJson{
        return ConferenceJson(
            id = this.id,
            category = this.category,
            dateEnd = this.dateEnd?:"",
            dateStart = this.dateStart?:"",
            description = this.description?:"",
            eventsJson = this.events?.map { it.toJson() }?.toMutableList()?: mutableListOf(),
            isCancelled = this.isCancelled,
            location = this.location?:"",
            name = this.name?:"",
            organizerId = null,
            status = this.status,
            tariffsJson = this.tariffs?.map { it.toJson() }?.toMutableList()?: mutableListOf(),
            organizerLogin = this.organizerLogin?:"",
            photoUrl = this.photoUrl?: ""
        )
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
        parcel.writeInt(organizerId?:-1)
        parcel.writeInt(status)
        parcel.writeTypedList(tariffs)
        parcel.writeString(organizerLogin)
        parcel.writeString(photoUrl)
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
