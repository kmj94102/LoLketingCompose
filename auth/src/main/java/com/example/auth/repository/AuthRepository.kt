package com.example.auth.repository

import android.content.Context
import com.example.auth.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun naverLogin(context: Context): Flow<Unit>

    fun fetchNaverProfile(): Flow<UserInfo>

    fun kakaoLogin(context: Context): Flow<String>

    fun fetchKakaoProfile(): Flow<String>

    fun join(userInfo: UserInfo): Flow<String>

}