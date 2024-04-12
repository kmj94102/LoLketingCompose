package com.example.network.repository

import com.example.network.model.Board
import com.example.network.model.BoardDetail
import com.example.network.model.BoardWriteInfo
import com.example.network.model.Comment
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    fun insertBoard(info: BoardWriteInfo): Flow<String>

    fun fetchBoardList(skip: Int, limit: Int): Flow<List<Board>>

    fun fetchBoardDetail(boardId: Int): Flow<BoardDetail>

    fun deleteBoard(boardId: Int): Flow<String>

    fun insertComment(contents: String, boardId: Int): Flow<List<Comment>>

    fun deleteComment(commentId: Int, boardId: Int): Flow<List<Comment>>

    fun updateBoardLike(boardId: Int): Flow<Board>
}