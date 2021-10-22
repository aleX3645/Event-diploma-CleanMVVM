package com.alex3645.feature_auth.domain.repository

import com.alex3645.feature_auth.data.model.AccResponse
import com.alex3645.feature_auth.data.model.AuthRequest
import com.alex3645.feature_auth.data.model.UserRegJson

interface AuthRepository {
    suspend fun authAsUser(authRequest: AuthRequest) : AccResponse

    suspend fun registerAsUser(userReg: UserRegJson) : AccResponse

    suspend fun authAsOrganiser(authRequest: AuthRequest) : AccResponse

    suspend fun registerAsOrganiser(userReg: UserRegJson) : AccResponse
}