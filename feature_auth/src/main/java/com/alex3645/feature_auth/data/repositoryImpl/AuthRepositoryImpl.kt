package com.alex3645.feature_auth.data.repositoryImpl

import com.alex3645.feature_auth.data.database.AccountAuthDao
import com.alex3645.feature_auth.data.database.model.AccountEntity
import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.data.network.service.ApiRetrofitAuthService
import com.alex3645.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val conferenceService: ApiRetrofitAuthService,
    private val database: AccountAuthDao
)
    : AuthRepository{

    override suspend fun auth(authRequest: AuthRequest) : AccResponse {
        val accResponse = conferenceService.auth(authRequest)

        if(accResponse.success){
            saveAccount(AccountEntity(authRequest.login, authRequest.passwordHash))
        }

        return accResponse
    }

    override suspend fun register(userReg: UserRegJson) : AccResponse {

        val accResponse = conferenceService.regAsUser(userReg)

        if(accResponse.success){
            database.insertAccount(AccountEntity(userReg.login, userReg.password))
        }

        return accResponse
    }

    suspend fun saveAccount(accountEntity: AccountEntity){
        database.insertAccount(accountEntity)
    }
}