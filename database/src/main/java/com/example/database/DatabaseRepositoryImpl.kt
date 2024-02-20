package com.example.database

import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val client: DatabaseClient
) : DatabaseRepository {

    override suspend fun insertInfo(id: Int, email: String, nickname: String) =
        client.insertInfo(AuthEntity(id = id, email = email, nickname = nickname))

    override suspend fun isLogin(): Boolean =
        client.isLogin().getOrElse { false }

    override suspend fun getUserEmail(): String =
        client.getUserEmail().getOrElse { "" }

    override suspend fun getUserId(): Int =
        client.getUserId().getOrElse { 0 }

    override suspend fun logout() =
        client.logout()

}