package com.example.network.service

import com.example.network.model.Game
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
}