package com.example.network.model

data class Coupon(
    val id: Int,
    val name: String,
    val number: String,
    val isUsed: Boolean,
    val rp: Int,
    val timestamp: String,
) {
    fun getCouponName() = when(name) {
        "COUPON001" -> "신규 가입 쿠폰"
        else -> "룰렛 쿠폰"
    }
}

data class UpdateCouponItem(
    val id: String,
    val couponId: Int
)