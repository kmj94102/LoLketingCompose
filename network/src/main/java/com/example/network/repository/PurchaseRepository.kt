package com.example.network.repository

import com.example.network.model.Game
import com.example.network.model.Goods
import com.example.network.model.GoodsDetail
import com.example.network.model.ProductPurchase
import com.example.network.model.ReservationTicketItem
import com.example.network.model.ReservationItem
import com.example.network.model.TicketIdParam
import com.example.network.model.TicketInfo
import kotlinx.coroutines.flow.Flow

interface PurchaseRepository {
    fun fetchGameList(): Flow<List<Game>>

    fun fetchReservedSeats(gameId: Int): Flow<ReservationItem>

    fun reservationTicket(item: ReservationTicketItem): Flow<String>

    fun fetchTicketInfo(item: TicketIdParam): Flow<TicketInfo>

    fun cashCharging(cash: Int, gameId: Int): Flow<ReservationItem>

    fun fetchGoodsItems(): Flow<List<Goods>>

    fun fetchGoodsItemDetail(goodsId: Int): Flow<GoodsDetail>

    fun insertProductPurchase(item: List<ProductPurchase>): Flow<String>
}