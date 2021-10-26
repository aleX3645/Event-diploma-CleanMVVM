package com.alex3645.feature_conference_builder.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.alex3645.feature_conference_builder.data.model.TariffJson

data class Tariff(
    val id: Int,
    val conferenceId: Int,
    val cost: Double,
    val name: String?,
    val ticketsLeft: Int,
    val ticketsTotal: Int
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    fun toJson() : TariffJson {
        return TariffJson(
            id = this.id,
            conferenceId = this.conferenceId,
            cost = this.cost,
            name = this.name?:"",
            ticketsLeft = this.ticketsLeft,
            ticketsTotal = this.ticketsTotal
        )
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(conferenceId)
        parcel.writeDouble(cost)
        parcel.writeString(name)
        parcel.writeInt(ticketsLeft)
        parcel.writeInt(ticketsTotal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tariff> {
        override fun createFromParcel(parcel: Parcel): Tariff {
            return Tariff(parcel)
        }

        override fun newArray(size: Int): Array<Tariff?> {
            return arrayOfNulls(size)
        }
    }
}
