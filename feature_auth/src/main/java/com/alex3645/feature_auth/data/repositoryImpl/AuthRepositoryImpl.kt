package com.alex3645.feature_auth.data.repositoryImpl

import android.content.Context
import com.alex3645.base.android.SharedPreferencesManager
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
    private val database: AccountAuthDao,
    private val context: Context) : AuthRepository{

    private val sharedPreferences = SharedPreferencesManager(context)

    override suspend fun authAsUser(authRequest: AuthRequest) : AccResponse {
        val accResponse = conferenceService.authAsUser(authRequest)

        if(accResponse.success){
            saveAccount(AccountEntity(authRequest.login, authRequest.passwordHash), accResponse.message)
        }
        return accResponse
    }

    override suspend fun registerAsUser(userReg: UserRegJson) : AccResponse {
        val accResponse = conferenceService.regAsUser(userReg)

        if(accResponse.success){
            saveAccount(AccountEntity(userReg.login, userReg.password), accResponse.message)
        }
        return accResponse
    }

    override suspend fun authAsOrganiser(authRequest: AuthRequest): AccResponse {
        val accResponse = conferenceService.authAsOrganizer(authRequest)

        if(accResponse.success){
            sharedPreferences.setOrgFlag(true)
            saveAccount(AccountEntity(authRequest.login, authRequest.passwordHash), accResponse.message)
        }
        return accResponse
    }

    override suspend fun registerAsOrganiser(userReg: UserRegJson): AccResponse {
        val accResponse = conferenceService.regAsOrganizer(userReg)

        if(accResponse.success){
            sharedPreferences.setOrgFlag(true)
            saveAccount(AccountEntity(userReg.login, userReg.password), accResponse.message)
        }
        return accResponse
    }

    private suspend fun saveAccount(accountEntity: AccountEntity, token: String){
        sharedPreferences.saveUserData(accountEntity.login, token)
        database.insertAccount(accountEntity)
    }
}