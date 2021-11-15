package com.alex3645.feature_account.data.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AccountDao {
    @Query("DELETE FROM accounts WHERE login = :login")
    suspend fun deleteAccountWithLogin(login: String)
}