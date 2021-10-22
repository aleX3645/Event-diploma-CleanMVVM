package com.alex3645.feature_account.data.model

import com.google.gson.annotations.SerializedName

data class AccResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("success")
    val success: Boolean
)