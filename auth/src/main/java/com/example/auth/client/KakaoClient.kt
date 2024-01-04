package com.example.auth.client

import android.content.Context
import android.util.Log
import com.example.auth.exception.KakaoException
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class KakaoClient @Inject constructor() {
    companion object {
        private const val TAG = "KakaoClient"
    }

    suspend fun kakaoLogin(context: Context) = suspendCoroutine { cont ->
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                error.printStackTrace()
                cont.resumeWithException(KakaoException())
            } else if (token != null) {
                cont.resume(token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    error.printStackTrace()
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        cont.resumeWithException(KakaoException())
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    cont.resume(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    suspend fun kakaoLogout() = suspendCoroutine { cont ->
        UserApiClient.instance.logout { error ->
            if (error != null) {
                error.printStackTrace()
                cont.resumeWithException(KakaoException())
            } else {
                cont.resume("로그아웃 성공")
            }
        }
    }

    suspend fun kakaoUnlink() = suspendCoroutine { cont ->
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                error.printStackTrace()
                cont.resumeWithException(KakaoException())
            } else {
                cont.resume("연결 끊기 성공")
            }
        }
    }

    suspend fun kakaoProfile() = suspendCoroutine { cont ->
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                error.printStackTrace()
                cont.resumeWithException(KakaoException())
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" +
                        "\nageRange: ${user.kakaoAccount?.ageRange}" +
                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                        "\nbirthday: ${user.kakaoAccount?.birthday}" +
                        "\ngender: ${user.kakaoAccount?.gender}")
                cont.resume("")
            }
        }
    }

}