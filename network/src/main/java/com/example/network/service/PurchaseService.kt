package com.example.network.service

import com.example.network.model.Game
import com.example.network.model.Goods
import com.example.network.model.GoodsDetail
import com.example.network.model.IntIdParam
import com.example.network.model.ProductPurchase
import com.example.network.model.PurchaseInfo
import com.example.network.model.RefundInfo
import com.example.network.model.ReservationItem
import com.example.network.model.TicketInfoParam
import com.example.network.model.ReservationTicketItem
import com.example.network.model.TicketIdParam
import com.example.network.model.TicketInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PurchaseService {
    @POST("/purchase/select/game")
    suspend fun fetchGameList(): Response<List<Game>>

    @POST("/purchase/select/reservationInfo")
    suspend fun fetchReservedSeats(@Body item: TicketInfoParam): Response<ReservationItem>

    @POST("/purchase/insert/reservationTicket")
    suspend fun reservationTicket(@Body item: ReservationTicketItem): Response<List<Int>>

    @POST("/purchase/select/ticketInfo")
    suspend fun fetchTicketInfo(@Body item: TicketIdParam): Response<TicketInfo>

    @POST("/purchase/delete/tickets")
    suspend fun refundTicket(@Body item: RefundInfo): Response<String>

    @POST("/purchase/select/goodsItems")
    suspend fun fetchGoodsItems(): Response<List<Goods>>

    @POST("/purchase/select/goodsItemDetail")
    suspend fun fetchGoodsItemDetail(@Body item: IntIdParam): Response<GoodsDetail>

    @POST("/purchase/insert/items")
    suspend fun insertProductPurchase(@Body item: List<ProductPurchase>): Response<String>

    @POST("/purchase/select/purchaseInfo")
    suspend fun fetchPurchaseInfo(@Body item: IntIdParam): Response<PurchaseInfo>
}