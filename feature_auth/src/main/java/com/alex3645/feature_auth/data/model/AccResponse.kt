package com.alex3645.feature_auth.data.model

import com.google.gson.annotations.SerializedName

data class AccResponse(
    @SerializedName("message")
    val message: String = "",
    @SerializedName("success")
    val success: Boolean = false
)