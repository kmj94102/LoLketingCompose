package com.example.database

import javax.inject.Inject

class DatabaseClient @Inject constructor(
    private val dao: AuthDao
) {

    suspend fun insertInfo(authEntity: AuthEntity) = runCatching {
        dao.insertInfo(authEntity)
    }

    suspend fun isLogin() = runCatching {
        dao.isLogin() > 0
    }

    suspend fun getUserId() = runCatching {
        dao.getUserId()
    }

    suspend fun logout() = runCatching {
        dao.logout()
    }

}