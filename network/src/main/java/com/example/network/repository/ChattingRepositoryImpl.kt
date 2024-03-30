package com.example.network.repository

import com.example.network.client.ChattingClient
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ChattingRepositoryImpl @Inject constructor(
    private val client: ChattingClient
): ChattingRepository {
    override fun fetchChattingList(date: String) = flow {
        client.fetchChattingList(date)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }
}