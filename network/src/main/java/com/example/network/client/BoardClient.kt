package com.example.network.client

import com.example.network.model.BoardSearch
import com.example.network.model.BoardWrite
import com.example.network.model.CommentDelete
import com.example.network.model.CommentWrite
import com.example.network.service.BoardService
import com.example.network.util.result
import javax.inject.Inject

class BoardClient @Inject constructor(
    private val service: BoardService
) {
    suspend fun insertBoard(item: BoardWrite) = runCatching {
        service.insertBoard(item).result()
    }

    suspend fun fetchBoardList(item: BoardSearch) = runCatching {
        service.fetchBoardList(item).result()
    }

    suspend fun insertComment(item: CommentWrite) = runCatching {
        service.insertComment(item).result()
    }

    suspend fun deleteComment(item: CommentDelete) = runCatching {
        service.deleteComment(item).result()
    }
}