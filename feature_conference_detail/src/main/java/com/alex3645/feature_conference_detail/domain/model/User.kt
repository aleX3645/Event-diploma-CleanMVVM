package com.alex3645.feature_conference_detail.domain.model

import com.alex3645.feature_conference_detail.data.model.UserJson
import com.alex3645.feature_conference_detail.data.model.UserMessage

data class User(val id: Int,
                val login: String,
                val name: String,
                val surname: String,
                val description: String,
                val phone: String,
                val email: String){
    internal fun toJson() : UserJson {
        return UserJson(
            id = this.id,
            login = this.login,
            name = this.name,
            surname = this.surname,
            description = this.description,
            phone = this.phone,
            email = this.email
        )
    }

    internal fun toChatMessageUser() : UserMessage {
        return UserMessage(
            id = this.id,
            login = this.login,
            name1 = this.name,
            surname = this.surname,
            description = this.description,
            phone = this.phone,
            email = this.email
        )
    }
}
