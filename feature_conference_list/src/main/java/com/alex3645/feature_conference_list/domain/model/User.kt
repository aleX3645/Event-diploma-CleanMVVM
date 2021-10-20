package com.alex3645.feature_conference_list.domain.model

import com.alex3645.feature_conference_list.data.model.UserJson

data class User(val id: Int,
                val login: String,
                val name: String?,
                val surname: String?,
                val description: String?,
                val phone: String?,
                val email: String?)