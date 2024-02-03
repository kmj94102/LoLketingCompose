package com.example.database

interface DatabaseRepository {

    suspend fun insertInfo(id: String, nickname: String): Result<Unit>

    suspend fun isLogin(): Boolean

}