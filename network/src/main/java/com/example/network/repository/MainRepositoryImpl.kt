package com.example.network.repository

import com.example.database.DatabaseRepository
import com.example.network.client.MainClient
import com.example.network.model.ModifyInfo
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import kotlinx.coroutines.flow.Flow
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

    override fun fetchModifyInfo(): Flow<ModifyInfo> = flow {
        val userId = databaseRepository.getUserId()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchMyInfo(userId)
            .onSuccess { emit(it.toModifyInfo()) }
            .onFailure { throw it }
    }

    override suspend fun fetchCashInfo() = flow {
        val userId = databaseRepository.getUserId()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchCashInfo(userId)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override suspend fun updateCashCharging(item: UpdateCashItem) = flow {
        client
            .updateCashCharging(item)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override suspend fun fetchCouponList(id: String) = flow {
        client
            .fetchCouponList(id)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override suspend fun updateUsingCoupon(item: UpdateCouponItem) = flow {
        client
            .updateUsingCoupon(item)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override suspend fun updateMyInfo(item: ModifyInfo) = flow {
        client
            .updateMyInfo(item)
            .onSuccess { emit(Unit) }
            .onFailure { throw it }
    }
}