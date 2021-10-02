package com.alex3645.feature_auth.data.repositoryImpl

import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.data.model.UserRegJson
import com.alex3645.feature_auth.data.network.service.ApiRetrofitAuthService
import com.alex3645.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
private val conferenceService: ApiRetrofitAuthService
): AuthRepository{
    override suspend fun auth(authRequest: AuthRequest) : AccResponse {
        return conferenceService.auth(authRequest)
    }

    override suspend fun register(userReg: UserRegJson) : AccResponse {
        return conferenceService.regAsUser(userReg)
    }
}