package com.example.database

import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val client: DatabaseClient
) : DatabaseRepository {

    override suspend fun insertInfo(id: String, nickname: String) =
        client.insertInfo(AuthEntity(id = id, nickname = nickname))

    override suspend fun isLogin(): Boolean =
        client.isLogin().getOrElse { false }

}