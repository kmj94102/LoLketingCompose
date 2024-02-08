package com.example.network.model

data class MyCash(
    val cash: Int
)

data class UpdateCashItem(
    val id: String,
    val cash: Int
)