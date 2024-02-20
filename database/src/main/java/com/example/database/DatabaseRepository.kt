package com.example.database

interface DatabaseRepository {

    suspend fun insertInfo(id: Int, email: String, nickname: String): Result<Unit>

    suspend fun isLogin(): Boolean

    suspend fun getUserEmail(): String

    suspend fun getUserId(): Int

    suspend fun logout(): Result<Unit>

}