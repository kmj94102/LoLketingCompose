package com.example.auth.service

import com.example.auth.model.LoginInfo
import com.example.auth.model.SocialLoginInfo
import com.example.auth.model.UserInfo
import com.example.database.AuthEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("/user/join/email")
    suspend fun join(@Body item: UserInfo): Response<String>

    @POST("/user/login/email")
    suspend fun emailLogin(@Body item: LoginInfo): Response<AuthEntity>

    @POST("/user/login/social")
    suspend fun socialLogin(@Body item: SocialLoginInfo): Response<AuthEntity>

}