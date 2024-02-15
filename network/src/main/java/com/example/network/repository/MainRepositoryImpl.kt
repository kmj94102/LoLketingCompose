package com.example.network.repository

import com.example.database.DatabaseRepository
import com.example.network.client.MainClient
import com.example.network.model.IdParam
import com.example.network.model.ModifyInfo
import com.example.network.model.RouletteCouponUpdateItem
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import com.example.network.model.UserIdParam
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

    override fun fetchCashInfo() = flow {
        val userId = databaseRepository.getUserId()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchCashInfo(userId)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun updateCashCharging(item: UpdateCashItem) = flow {
        client
            .updateCashCharging(item)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun fetchCouponList(id: String) = flow {
        client
            .fetchCouponList(id)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun updateUsingCoupon(item: UpdateCouponItem) = flow {
        client
            .updateUsingCoupon(item)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun updateMyInfo(item: ModifyInfo) = flow {
        item.checkValidation()
        client
            .updateMyInfo(item)
            .onSuccess { emit(Unit) }
            .onFailure { throw it }
    }

    override fun fetchNewUserCoupon() = flow {
        val userId = databaseRepository.getUserId()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchNewUserCoupon(IdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun insertNewUserCoupon(userId: Int) = flow {
        client
            .insertNewUserCoupon(UserIdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override suspend fun insertRouletteCoupon(id: Int, rp: Int) =
        client
            .insertRouletteCoupon(RouletteCouponUpdateItem(id, rp))

    override fun fetchRouletteCount(userId: Int) = flow {
        client
            .fetchRouletteCount(UserIdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

}