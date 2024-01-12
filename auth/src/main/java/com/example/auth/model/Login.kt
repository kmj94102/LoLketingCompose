package com.example.auth.model

data class LoginInfo(
    val id: String,
    val password: String
)

data class SocialLoginInfo(
    val type: String,
    val id: String
)