package com.example.auth.repository

import android.content.Context
import com.example.auth.client.AuthClient
import com.example.auth.client.KakaoClient
import com.example.auth.client.NaverClient
import com.example.auth.exception.KakaoException
import com.example.auth.exception.NaverException
import com.example.auth.model.LoginInfo
import com.example.auth.model.SocialLoginInfo
import com.example.auth.model.UserInfo
import com.example.database.DatabaseRepository
import com.example.network.util.NetworkException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val naverClient: NaverClient,
    private val kakaoClient: KakaoClient,
    private val authClient: AuthClient,
    private val databaseRepository: DatabaseRepository
): AuthRepository {

    override fun join(userInfo: UserInfo) = flow {
        userInfo.checkValidation()
        authClient.join(userInfo)
            .onSuccess { result ->
                databaseRepository
                    .insertInfo(id = userInfo.id, nickname = userInfo.nickname)
                    .onSuccess { emit(result) }
                    .onFailure { throw Exception("데이터베이스 오류") }
            }
            .onFailure { throw it }
    }

    override fun emailLogin(loginInfo: LoginInfo) = flow {
        authClient.emailLogin(loginInfo)
            .onSuccess {
                databaseRepository
                    .insertInfo(id = it.id, nickname = it.nickname)
                    .onSuccess { emit(Unit) }
                    .onFailure { throw Exception("데이터베이스 오류") }
            }
            .onFailure { throw it }
    }

    override fun naverLogin(context: Context) = flow {
        naverClient.naverLogin(context)
        val info = naverClient.naverProfile() ?: run {
            naverClient.naverUnlink()
            naverClient.logout()
            throw NaverException()
        }

        socialLogin(info)
            .onSuccess {
                databaseRepository
                    .insertInfo(id = it.id, nickname = it.nickname)
                    .onSuccess { emit(null) }
                    .onFailure { throw Exception("데이터베이스 오류") }
            }
            .onFailure { exception ->
                if(exception is NetworkException && exception.code == 501) {
                    emit(info)
                } else {
                    throw exception
                }
            }
    }
    override fun kakaoLogin(context: Context) = flow {
        kakaoClient.kakaoLogin(context)
        val info = kakaoClient.kakaoProfile() ?: run {
            kakaoClient.kakaoUnlink()
            kakaoClient.kakaoLogout()
            throw KakaoException()
        }

        socialLogin(info)
            .onSuccess {
                databaseRepository
                    .insertInfo(id = it.id, nickname = it.nickname)
                    .onSuccess { emit(null) }
                    .onFailure { throw Exception("데이터베이스 오류") }
            }
            .onFailure { exception ->
                if(exception is NetworkException && exception.code == 501) {
                    emit(info)
                } else {
                    throw exception
                }
            }
    }

    private suspend fun socialLogin(info: UserInfo) =
        authClient
            .socialLogin(
                SocialLoginInfo(type = info.type, id = info.id)
            )

    override suspend fun naverUnlink() {
        naverClient.naverUnlink()
    }

}