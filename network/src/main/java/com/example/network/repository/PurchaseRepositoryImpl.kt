package com.example.network.repository

import com.example.database.DatabaseRepository
import com.example.database.GoodsEntity
import com.example.network.client.MainClient
import com.example.network.client.PurchaseClient
import com.example.network.model.IntIdParam
import com.example.network.model.ProductPurchase
import com.example.network.model.RefundInfo
import com.example.network.model.ReservationTicketItem
import com.example.network.model.TicketIdParam
import com.example.network.model.TicketInfoParam
import com.example.network.model.UpdateCashItem
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PurchaseRepositoryImpl @Inject constructor(
    private val client: PurchaseClient,
    private val mailClient: MainClient,
    private val databaseRepository: DatabaseRepository
): PurchaseRepository{
    override fun fetchGameList() = flow {
        client
            .fetchGameList()
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun fetchReservedSeats(gameId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        client
            .fetchReservedSeats(
                TicketInfoParam(
                    gameId = gameId,
                    userId = userId
                )
            )
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun reservationTicket(item: ReservationTicketItem) = flow {
        item.checkValidation()
        client
            .reservationTicket(item)
            .onSuccess { ids -> emit(ids.map { "$it" }.reduce { acc, s -> "$acc,$s" }) }
            .onFailure { throw it }
    }

    override fun fetchTicketInfo(item: TicketIdParam) = flow {
        client
            .fetchTicketInfo(item)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun refundTicket(reservationIds: List<Int>) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        client
            .refundTicket(
                RefundInfo(
                    userId = userId,
                    reservationIds = reservationIds
                )
            )
            .onSuccess { emit(Unit) }
            .onFailure { throw it }
    }

    override fun cashChargingAndReservationInfo(cash: Int, gameId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        mailClient
            .updateCashCharging(
                UpdateCashItem(
                    id = userId,
                    cash = cash
                )
            )
            .onFailure { throw it }

        client
            .fetchReservedSeats(
                TicketInfoParam(
                    gameId = gameId,
                    userId = userId
                )
            )
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun cashCharging(cash: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        mailClient
            .updateCashCharging(
                UpdateCashItem(
                    id = userId,
                    cash = cash
                )
            )
            .onSuccess { emit(it.cash) }
            .onFailure { throw it }
    }

    override fun fetchGoodsItems() = flow {
        client
            .fetchGoodsItems()
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun fetchGoodsItemDetail(goodsId: Int) = flow {
        client
            .fetchGoodsItemDetail(
                IntIdParam(goodsId)
            )
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun insertProductPurchase(items: List<GoodsEntity>) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        client
            .insertProductPurchase(
                items.map {
                    ProductPurchase(
                        userId = userId,
                        goodsId = it.goodsId,
                        amount = it.amount,
                        productsPrice = it.price * it.amount
                    )
                }
            )
            .onSuccess {
                emit(it)
                databaseRepository.deleteItems(items)
            }
            .onFailure { throw it }
    }

    override fun fetchPurchaseInfo() = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) {
            throw Exception("유저 정보가 없습니다.")
        }

        client
            .fetchPurchaseInfo(IntIdParam(userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }
}