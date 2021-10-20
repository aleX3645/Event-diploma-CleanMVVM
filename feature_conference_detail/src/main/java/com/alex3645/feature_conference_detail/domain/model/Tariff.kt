package com.alex3645.feature_conference_detail.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Tariff(
    val conferenceId: Int,
    val cost: Double,
    val id: Int,
    val name: String?,
    val ticketsLeft: Int,
    val ticketsTotal: Int
)