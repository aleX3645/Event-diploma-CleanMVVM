package com.alex3645.feature_account.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alex3645.feature_auth.data.database.model.AccountEntity

@Dao
interface AccountDao {
    @Query("DELETE FROM accounts WHERE login = :login")
    suspend fun deleteAccountWithLogin(login: String)
}