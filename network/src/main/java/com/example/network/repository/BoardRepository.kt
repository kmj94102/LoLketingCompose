package com.example.network.repository

import com.example.network.model.Board
import com.example.network.model.BoardSearch
import com.example.network.model.BoardWriteInfo
import com.example.network.model.Comment
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    fun insertBoard(info: BoardWriteInfo): Flow<String>

    fun fetchBoardList(item: BoardSearch): Flow<List<Board>>

    fun insertComment(contents: String, boardId: Int): Flow<String>

    fun deleteComment(commentId: Int, boardId: Int): Flow<Comment>
}