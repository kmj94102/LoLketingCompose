package com.example.network.repository

import com.example.database.DatabaseRepository
import com.example.network.client.MainClient
import com.example.network.model.StringIdParam
import com.example.network.model.ModifyInfo
import com.example.network.model.PurchaseHistoryInfo
import com.example.network.model.RouletteCouponUpdateItem
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import com.example.network.model.IntIdParam
import com.example.network.model.RouletteCount
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val client: MainClient,
    private val databaseRepository: DatabaseRepository
) : MainRepository {
    override fun fetchMyInfo() = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .fetchMyInfo(userId)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun fetchModifyInfo(): Flow<ModifyInfo> = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .fetchMyInfo(userId)
            .onSuccess { emit(it.toModifyInfo()) }
            .onFailure { throw it }
    }

    override fun fetchCashInfo() = flow {
        val userId = databaseRepository.getUserEmail()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchCashInfo(userId)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun updateCashCharging(cash: Int) = flow {
        if (cash < 1_000) throw Exception("최소 충전 금액은 1,000원 입니다. 충전 금액을 확인해주세요.")
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .updateCashCharging(
                UpdateCashItem(
                    id = userId,
                    cash = cash
                )
            )
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
        val userId = databaseRepository.getUserEmail()
        if (userId.isEmpty()) throw Exception("유저 정보가 없습니다.")

        client
            .fetchNewUserCoupon(StringIdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun insertNewUserCoupon() = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .insertNewUserCoupon(IntIdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override suspend fun insertRouletteCoupon(rp: Int): Result<RouletteCount> {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        return client
            .insertRouletteCoupon(RouletteCouponUpdateItem(userId, rp))
    }

    override fun fetchRouletteCount() = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }


        client
            .fetchRouletteCount(IntIdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun fetchTicketHistory() = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        client
            .fetchTicketHistory(IntIdParam(userId))
            .onSuccess {
                emit(PurchaseHistoryInfo.ticketHistoryListMapper(it))
            }
            .onFailure { throw it }
    }

    override fun fetchGoodsHistory() = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        client
            .fetchGoodsHistory(IntIdParam(userId))
            .onSuccess {
                emit(PurchaseHistoryInfo.goodsHistoryListMapper(it))
            }
            .onFailure { throw it }
    }

}