package com.example.auth.model

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
)

enum class UserInfoType(val type: String) {
    Email("email"),
    Naver("naver"),
    Kakao("kakao")
}
