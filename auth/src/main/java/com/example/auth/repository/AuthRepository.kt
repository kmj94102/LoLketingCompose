package com.example.auth.repository

import android.content.Context
import com.example.auth.model.LoginInfo
import com.example.auth.model.JoinInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun join(joinInfo: JoinInfo): Flow<String>

    fun emailLogin(loginInfo: LoginInfo): Flow<Unit>

    fun naverLogin(context: Context): Flow<JoinInfo?>

    fun kakaoLogin(context: Context): Flow<JoinInfo?>

    suspend fun naverUnlink()

    suspend fun isLogin(): Boolean

}