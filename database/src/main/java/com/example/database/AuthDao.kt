package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface AuthDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(authEntity: AuthEntity)

}