package com.example.network.client

import com.example.network.model.BoardIdInfoParam
import com.example.network.model.BoardModify
import com.example.network.model.BoardSearch
import com.example.network.model.BoardWrite
import com.example.network.model.CommentDelete
import com.example.network.model.CommentWrite
import com.example.network.model.LikeUpdate
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

    suspend fun fetchBoardDetail(item: BoardIdInfoParam) = runCatching {
        service.fetchBoardDetail(item).result()
    }

    suspend fun updateBoard(item: BoardModify) = runCatching {
        service.updateBoard(item).result()
    }

    suspend fun deleteBoard(item: BoardIdInfoParam) = runCatching {
        service.deleteBoard(item).result()
    }

    suspend fun insertComment(item: CommentWrite) = runCatching {
        service.insertComment(item).result()
    }

    suspend fun deleteComment(item: CommentDelete) = runCatching {
        service.deleteComment(item).result()
    }

    suspend fun updateBoardLike(item: LikeUpdate) = runCatching {
        service.updateBoardLike(item).result()
    }
}