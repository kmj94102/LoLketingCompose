package com.example.auth.repository

import android.content.Context
import com.example.auth.model.LoginInfo
import com.example.auth.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun join(userInfo: UserInfo): Flow<String>

    fun emailLogin(loginInfo: LoginInfo): Flow<Unit>

    fun naverLogin(context: Context): Flow<UserInfo?>

    fun kakaoLogin(context: Context): Flow<UserInfo?>

    suspend fun naverUnlink()

    suspend fun isLogin(): Boolean

}