package com.alex3645.feature_conference_builder.data.model

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)