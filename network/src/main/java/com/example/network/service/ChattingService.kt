package com.example.network.service

import com.example.network.model.ChattingListParam
import com.example.network.model.ChattingRoomInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChattingService {
    @POST("/chatting/select/chattingList")
    suspend fun fetchChattingList(@Body item: ChattingListParam): Response<List<ChattingRoomInfo>>
}