package com.alex3645.feature_auth.domain.repository

import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.data.model.UserRegJson

interface AuthRepository {
    suspend fun auth(authRequest: AuthRequest) : AccResponse

    suspend fun register(userReg: UserRegJson) : AccResponse
}