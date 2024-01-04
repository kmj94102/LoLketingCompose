package com.example.auth.repository

import android.content.Context
import com.example.auth.client.NaverClient
import com.example.auth.exception.NaverException
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val naverClient: NaverClient
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

}