package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(authEntity: AuthEntity)

    @Query("SELECT COUNT(*) FROM AuthEntity")
    suspend fun isLogin(): Int

    @Query("SELECT id FROM AuthEntity LIMIT 1")
    suspend fun getUserId(): String

    @Query("DELETE FROM AuthEntity")
    suspend fun logout()

}