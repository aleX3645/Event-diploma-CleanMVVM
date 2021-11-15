package com.alex3645.feature_user_account.domain.repository

import com.alex3645.feature_user_account.domain.data.User

interface UserRepository{
    suspend fun loadUserById(id: Int) : User
}