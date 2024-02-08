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
    val lolketingId: Int,
    val grade: String,
    val point: Int,
    val cash: Int,
    val couponId: Int,
    val totalCoupons: Int,
    val availableCoupons: Int
) {
    fun formatMobileNumber(): String {
        val regex = Regex("(\\d{3})(\\d{3,4})(\\d{4})")
        return mobile.replace(regex, "$1-$2-$3")
    }

    fun toModifyInfo() = ModifyInfo(
        id = id,
        nickname = nickname,
        mobile = mobile,
        address = address
    )

    companion object {
        fun init() = MyInfo(
            nickname = "",
            id = "",
            mobile = "",
            address = "",
            lolketingId = 0,
            grade = "",
            point = 0,
            cash = 0,
            couponId = 0,
            totalCoupons = 0,
            availableCoupons = 0
        )
    }
}

data class ModifyInfo(
    val id: String,
    val nickname: String,
    val mobile: String,
    val address: String
) {
    companion object {
        fun init() = ModifyInfo(
            id =  "",
            nickname = "",
            mobile = "",
            address = ""
        )
    }
}