package com.alex3645.feature_auth.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.alex3645.feature_auth.data.database.model.AccountEntity

@Dao
interface AccountAuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(accounts: AccountEntity)
}