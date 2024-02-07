package com.example.network.model

import com.google.gson.annotations.SerializedName

data class IdParam(
    val id: String
)

data class MyInfo(
    val nickname: String,
    val id: String,
    val mobile: String,
    val address: String,
    val grade: String,
    val point: Int,
    val cash: Int,
    @SerializedName("total_coupons")
    val totalCoupons: Int,
    @SerializedName("unused_coupons")
    val unusedCoupons: Int
) {
    fun formatMobileNumber(): String {
        val regex = Regex("(\\d{3})(\\d{3,4})(\\d{4})")
        return mobile.replace(regex, "$1-$2-$3")
    }

    companion object {
        fun init() = MyInfo(
            nickname = "",
            id = "",
            mobile = "",
            address = "",
            grade = "",
            point = 0,
            cash = 0,
            totalCoupons = 0,
            unusedCoupons = 0
        )
    }
}
