package com.alex3645.feature_user_account.data.repositoryImpl

import com.alex3645.feature_user_account.data.network.ApiRetrofitAccountUserService
import com.alex3645.feature_user_account.domain.data.User
import com.alex3645.feature_user_account.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val service: ApiRetrofitAccountUserService): UserRepository {
    override suspend fun loadUserById(id: Int) : User {
        return service.loadUserById(id).toDomainModel()
    }
}