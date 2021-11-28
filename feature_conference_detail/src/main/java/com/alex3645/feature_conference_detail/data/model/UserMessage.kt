package com.alex3645.feature_conference_detail.data.model

import com.google.gson.annotations.SerializedName
import com.stfalcon.chatkit.commons.models.IUser

class UserMessage (
    @SerializedName("id")
    val id: Int = -1,
    @SerializedName("login")
    val login: String = "login",
    @SerializedName("name")
    var name1: String = "",
    @SerializedName("surname")
    val surname: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("photo_url")
    val photoUrl: String = ""
): IUser{
    override fun getId(): String {
        return login
    }

    override fun getName(): String {
        return "$name1 $surname"
    }

    fun setName(name: String) {
        this.name = name
    }

    override fun getAvatar(): String {
        return ""
    }

}