package com.example.network.client

import com.example.network.model.ChattingListParam
import com.example.network.service.ChattingService
import com.example.network.util.result
import javax.inject.Inject

class ChattingClient @Inject constructor(
    private val service: ChattingService
) {
    suspend fun fetchChattingList(date: String) = runCatching {
        service.fetchChattingList(ChattingListParam(date)).result()
    }
}