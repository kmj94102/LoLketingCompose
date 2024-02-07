package com.example.network.repository

import com.example.database.DatabaseRepository
import com.example.network.client.MainClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val client: MainClient,
    private val databaseRepository: DatabaseRepository
) : MainRepository {
    override fun fetchMyInfo() = flow {
        val userId = databaseRepository.getUserId()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchMyInfo(userId)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }
}