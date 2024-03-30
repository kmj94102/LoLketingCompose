package com.example.network.repository

import com.example.network.model.ChattingRoomInfo
import kotlinx.coroutines.flow.Flow

interface ChattingRepository {
    fun fetchChattingList(date: String): Flow<List<ChattingRoomInfo>>
}