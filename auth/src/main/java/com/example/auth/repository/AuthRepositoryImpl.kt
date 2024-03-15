package com.example.auth.repository

import android.content.Context
import com.example.auth.client.AuthClient
import com.example.auth.client.KakaoClient
import com.example.auth.client.NaverClient
import com.example.auth.exception.KakaoException
import com.example.auth.exception.NaverException
import com.example.auth.model.LoginInfo
import com.example.auth.model.SocialLoginInfo
import com.example.auth.model.JoinInfo
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

    override fun join(joinInfo: JoinInfo) = flow {
        joinInfo.checkValidation()
        authClient.join(joinInfo)
            .onSuccess { result ->
                databaseRepository
                    .insertInfo(id = result, email = joinInfo.id, nickname = joinInfo.nickname)
                    .onSuccess { emit("회원가입 완료") }
                    .onFailure { throw Exception("데이터베이스 오류") }
            }
            .onFailure { throw it }
    }

    override fun emailLogin(loginInfo: LoginInfo) = flow {
        if (loginInfo.id.isEmpty() || loginInfo.password.isEmpty()) {
            throw Exception("아이디 또는 비밀번호를 입력해주세요.")
        }

        authClient.emailLogin(loginInfo)
            .onSuccess {
                databaseRepository
                    .insertInfo(id = it.id, email = it.email, nickname = it.nickname)
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
                    .insertInfo(id = it.id, email = it.email, nickname = it.nickname)
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
                    .insertInfo(id = it.id, email = it.email, nickname = it.nickname)
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

    private suspend fun socialLogin(info: JoinInfo) =
        authClient
            .socialLogin(
                SocialLoginInfo(type = info.type, id = info.id)
            )

    override suspend fun logout() = databaseRepository.logout()

    override suspend fun withdrawal(): Result<Unit> = runCatching {
        val id = databaseRepository.getUserEmail()
        if (id.isEmpty()) throw Exception("유저 정보가 없습니다.")

        authClient
            .withdrawal(id)
            .onSuccess {
                if (it.type == "kakao") {
                    kakaoClient.kakaoUnlink()
                } else if (it.type == "naver") {
                    naverClient.naverUnlink()
                }
            }
            .onFailure { throw it }

        databaseRepository.logout()
    }

    override suspend fun isLogin() = databaseRepository.isLogin()

}