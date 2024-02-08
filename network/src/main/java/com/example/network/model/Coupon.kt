package com.example.network.model

data class Coupon(
    val id: Int,
    val name: String,
    val number: String,
    val isUsed: Boolean,
    val rp: Int,
    val timestamp: String,
)

data class UpdateCouponItem(
    val id: String,
    val couponId: Int
)