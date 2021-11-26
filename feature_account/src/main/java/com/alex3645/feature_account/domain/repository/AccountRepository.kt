package com.alex3645.feature_account.domain.repository

import com.alex3645.feature_account.data.model.AccResponse
import com.alex3645.feature_account.domain.model.User
import okhttp3.MultipartBody
import java.io.File

interface AccountRepository {
    suspend fun loadAccountByLogin(login: String) : User
    suspend fun deleteAccountWithLogin(login: String)
    suspend fun editAccountWithLogin(token: String, login: String, user: User) : AccResponse
    suspend fun uploadImage(token: String, file: MultipartBody.Part) : AccResponse
}