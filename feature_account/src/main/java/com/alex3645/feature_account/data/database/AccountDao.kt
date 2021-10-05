package com.alex3645.feature_auth.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alex3645.feature_auth.data.database.model.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts WHERE login = :login")
    suspend fun getAccountWithLogin(login: String) : AccountEntity

    @Query("SELECT * FROM accounts")
    suspend fun getAll() : List<AccountEntity>
}