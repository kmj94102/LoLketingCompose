package com.example.network.repository

import com.example.network.model.Game
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
}