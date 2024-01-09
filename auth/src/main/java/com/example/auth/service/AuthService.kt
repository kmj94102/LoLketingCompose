package com.example.auth.service

import com.example.auth.model.UserInfo
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/user/join/email")
    suspend fun join(@Body item: UserInfo): String

}