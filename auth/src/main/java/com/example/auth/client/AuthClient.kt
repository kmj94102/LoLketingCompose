package com.example.auth.client

import com.example.auth.model.UserInfo
import com.example.auth.service.AuthService
import com.example.network.util.result
import javax.inject.Inject

class AuthClient @Inject constructor(
    private val service: AuthService
) {

    suspend fun join(item: UserInfo) = runCatching {
        service.join(item).result()
    }

}