package com.example.auth.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    val type: String,
    val id: String,
    val password: String,
    val nickname: String,
    val gender: String,
    val birthday: String,
    val birthYear: String,
    val mobile: String,
    val address: String,
): Parcelable {
    companion object {
        fun create() = UserInfo(
            type = "",
            id = "test@test.com",
            password = "",
            nickname = "",
            gender = "",
            birthday = "",
            birthYear = "",
            mobile = "",
            address = ""
        )
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(type)
        parcel.writeString(id)
        parcel.writeString(password)
        parcel.writeString(nickname)
        parcel.writeString(gender)
        parcel.writeString(birthday)
        parcel.writeString(birthYear)
        parcel.writeString(mobile)
        parcel.writeString(address)
    }
}

enum class UserInfoType(val type: String) {
    Email("email"),
    Naver("naver"),
    Kakao("kakao")
}
