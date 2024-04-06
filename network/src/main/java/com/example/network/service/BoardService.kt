package com.example.network.service

import com.example.network.model.Board
import com.example.network.model.BoardSearch
import com.example.network.model.BoardWrite
import com.example.network.model.Comment
import com.example.network.model.CommentDelete
import com.example.network.model.CommentWrite
import com.example.network.model.LikeUpdate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface BoardService {
    @POST("/board/insert/board")
    suspend fun insertBoard(@Body item: BoardWrite): Response<String>

    @POST("/board/select/boards")
    suspend fun fetchBoardList(@Body item: BoardSearch): Response<List<Board>>

    @POST("/board/insert/comment")
    suspend fun insertComment(@Body item: CommentWrite): Response<String>

    @POST("/board/delete/comment")
    suspend fun deleteComment(@Body item: CommentDelete): Response<Comment>

    @POST("/board/update/boardLike")
    suspend fun updateBoardLike(@Body item: LikeUpdate): Response<Board>
}