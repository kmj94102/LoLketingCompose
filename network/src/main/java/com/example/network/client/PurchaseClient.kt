package com.example.network.client

import com.example.network.model.TicketInfoParam
import com.example.network.model.ReservationTicketItem
import com.example.network.model.TicketIdParam
import com.example.network.service.PurchaseService
import com.example.network.util.result
import javax.inject.Inject

class PurchaseClient @Inject constructor(
    private val service: PurchaseService
) {
    suspend fun fetchGameList() = runCatching {
        service.fetchGameList().result()
    }

    suspend fun fetchReservedSeats(item: TicketInfoParam) = runCatching {
        service.fetchReservedSeats(item).result()
    }

    suspend fun reservationTicket(item: ReservationTicketItem) = runCatching {
        service.reservationTicket(item).result()
    }

    suspend fun fetchTicketInfo(item: TicketIdParam) = runCatching {
        service.fetchTicketInfo(item).result()
    }
}