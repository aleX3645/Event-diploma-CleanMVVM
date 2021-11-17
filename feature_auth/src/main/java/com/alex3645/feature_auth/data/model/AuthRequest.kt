package com.alex3645.feature_auth.data.model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    @SerializedName("device_id")
    val deviceId: String = "",
    @SerializedName("login")
    val login: String = "login",
    @SerializedName("password_hash")
    val passwordHash: String = "password"
)