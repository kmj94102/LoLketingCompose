package com.example.auth.repository

import android.content.Context
import com.example.auth.client.AuthClient
import com.example.auth.client.KakaoClient
import com.example.auth.client.NaverClient
import com.example.auth.exception.NaverException
import com.example.auth.exception.getFailureThrow
import com.example.auth.model.UserInfo
import com.example.database.DatabaseRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val naverClient: NaverClient,
    private val kakaoClient: KakaoClient,
    private val authClient: AuthClient,
    private val databaseRepository: DatabaseRepository
): AuthRepository {

    override fun naverLogin(context: Context) = flow {
        emit(naverClient.naverLogin(context))
    }

    override fun fetchNaverProfile() = flow {
        naverClient.naverProfile()?.let {
            emit(it)
        } ?: run {
            naverClient.naverUnlink()
            naverClient.logout()
            throw NaverException()
        }
    }

    override fun kakaoLogin(context: Context) = flow {
        emit(kakaoClient.kakaoLogin(context))
    }

    override fun fetchKakaoProfile() = flow {
        emit(kakaoClient.kakaoProfile())
    }

    override fun join(userInfo: UserInfo) = flow {
        authClient.join(userInfo)
            .onSuccess { result ->
                databaseRepository
                    .insertInfo(id = userInfo.id, nickname = userInfo.nickname)
                    .onSuccess { emit(result) }
                    .onFailure { throw Exception("데이터베이스 오류") }
            }
            .getFailureThrow()
    }

}