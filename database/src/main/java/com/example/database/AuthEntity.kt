package com.example.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthEntity(
    @PrimaryKey(autoGenerate = true)
    val index: Int = 0,
    val id: String,
    val nickname: String
)