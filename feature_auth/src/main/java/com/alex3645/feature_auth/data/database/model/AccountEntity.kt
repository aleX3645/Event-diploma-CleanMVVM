package com.alex3645.feature_auth.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "accounts")
data class AccountEntity(@PrimaryKey(autoGenerate = false)
                         val login: String,
                         val password: String)