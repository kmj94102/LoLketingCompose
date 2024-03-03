package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        AuthEntity::class,
        GoodsEntity::class
    ],
    version = 3,
    exportSchema = true
)
abstract class LoLketingDatabase: RoomDatabase() {
    abstract fun authDao(): AuthDao

    abstract fun goodsDao(): GoodsDao
}