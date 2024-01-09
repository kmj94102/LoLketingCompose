package com.example.database

import javax.inject.Inject

class DatabaseClient @Inject constructor(
    private val dao: AuthDao
) {

    suspend fun insertInfo(authEntity: AuthEntity) = runCatching {
        dao.insertInfo(authEntity)
    }

}