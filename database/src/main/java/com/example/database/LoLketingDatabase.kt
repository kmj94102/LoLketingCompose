package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        AuthEntity::class,
    ],
    version = 2,
    exportSchema = true
)
abstract class LoLketingDatabase: RoomDatabase() {
    abstract fun authDao(): AuthDao
}