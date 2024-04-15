package com.example.network.service

import com.example.network.model.Board
import com.example.network.model.BoardDetail
import com.example.network.model.BoardIdInfoParam
import com.example.network.model.BoardModify
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

    @POST("/board/select/boardDetail")
    suspend fun fetchBoardDetail(@Body item: BoardIdInfoParam): Response<BoardDetail>

    @POST("/board/update/board")
    suspend fun updateBoard(@Body item: BoardModify): Response<String>

    @POST("/board/delete/board")
    suspend fun deleteBoard(@Body item: BoardIdInfoParam): Response<String>

    @POST("/board/insert/comment")
    suspend fun insertComment(@Body item: CommentWrite): Response<List<Comment>>

    @POST("/board/update/comment")
    suspend fun updateComment(@Body item: Comment): Response<String>

    @POST("/board/delete/comment")
    suspend fun deleteComment(@Body item: CommentDelete): Response<List<Comment>>

    @POST("/board/update/boardLike")
    suspend fun updateBoardLike(@Body item: LikeUpdate): Response<Board>
}