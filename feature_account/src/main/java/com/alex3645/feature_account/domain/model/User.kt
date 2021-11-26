package com.alex3645.feature_account.domain.model

import com.alex3645.feature_account.data.model.UserJson

data class User(val id: Int,
                var login: String,
                var name: String,
                var surname: String,
                var description: String,
                var phone: String,
                var email: String,
                var photoUrl: String
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
