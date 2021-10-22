package com.alex3645.feature_account.data.repositoryImpl

import android.content.Context
import com.alex3645.base.android.SharedPreferencesManager
import com.alex3645.feature_account.data.database.AccountDao
import com.alex3645.feature_account.data.model.AccResponse
import com.alex3645.feature_account.data.network.service.ApiRetrofitAccountService
import com.alex3645.feature_account.domain.model.User
import com.alex3645.feature_account.domain.repository.AccountRepository

class AccountRepositoryImpl (
    private val accountDao: AccountDao,
    private val service: ApiRetrofitAccountService): AccountRepository {
    override suspend fun loadAccountByLogin(login: String): User {
        return service.loadUserByLogin(login).toDomainModel()
    }

    override suspend fun deleteAccountWithLogin(login: String) {
        accountDao.deleteAccountWithLogin(login)
    }

    override suspend fun editAccountWithLogin(
        token: String,
        login: String,
        user: User
    ): AccResponse {
        return service.editAccount(token, user, login)
    }
}