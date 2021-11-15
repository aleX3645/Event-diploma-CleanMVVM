package com.alex3645.feature_auth.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alex3645.feature_auth.data.database.model.AccountEntity

@Database(entities = [AccountEntity::class], version = 1, exportSchema = false)
abstract class AccountDatabase : RoomDatabase() {
    abstract fun accounts(): AccountAuthDao
}