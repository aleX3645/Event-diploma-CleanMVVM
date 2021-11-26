package com.alex3645.feature_search.domain.data

import com.alex3645.feature_search.data.model.UserJson
import com.google.gson.annotations.SerializedName

data class User(val id: Int,
                val login: String,
                val name: String,
                val surname: String,
                val description: String,
                val phone: String,
                val email: String,
                val photoUrl: String
){
    internal fun toJson() : UserJson {
        return UserJson(
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
