package com.example.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class GoodsEntity (
    @PrimaryKey(autoGenerate = true)
    val index: Int = 0,
    val category: String,
    val name: String,
    val price: Int,
    val amount: Int,
    val isChecked: Boolean = true,
    val image: String
)