package com.alex3645.feature_user_account.domain.repository

import com.alex3645.feature_user_account.data.network.ApiRetrofitAccountUserService
import com.alex3645.feature_user_account.domain.data.User
import javax.inject.Inject

interface UserRepository{
    suspend fun loadUserById(id: Int) : User
}