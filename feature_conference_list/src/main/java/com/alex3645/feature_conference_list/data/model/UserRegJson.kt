package com.alex3645.feature_conference_list.data.model

import com.alex3645.feature_conference_list.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserRegJson(
    @SerializedName("login")
    val login: String,
    @SerializedName("password_hash")
    val password: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("email")
    val email: String
)
