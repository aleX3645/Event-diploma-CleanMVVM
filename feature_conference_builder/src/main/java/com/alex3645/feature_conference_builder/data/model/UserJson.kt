package com.alex3645.feature_conference_builder.data.model

import com.alex3645.feature_conference_builder.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserJson(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String?,
    @SerializedName("surname")
    val surname: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("photo_url")
    val photoUrl: String? = ""
){
    internal fun toDomainModel() : User {
        return User(
            id = this.id,
            login = this.login,
            name = this.name,
            surname = this.surname,
            description = this.description,
            phone = this.phone,
            email = this.email,
            photoUrl = this.photoUrl
        )
    }
}