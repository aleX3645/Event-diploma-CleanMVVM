package com.alex3645.feature_conference_detail.domain.model

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
    )