package com.example.network.repository

import com.example.database.DatabaseRepository
import com.example.network.client.BoardClient
import com.example.network.client.FirebaseClient
import com.example.network.model.BoardIdInfoParam
import com.example.network.model.BoardSearch
import com.example.network.model.BoardWrite
import com.example.network.model.BoardWriteInfo
import com.example.network.model.CommentDelete
import com.example.network.model.CommentWrite
import com.example.network.model.LikeUpdate
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(
    private val client: BoardClient,
    private val firebaseClient: FirebaseClient,
    private val databaseRepository: DatabaseRepository
) : BoardRepository {
    override fun insertBoard(info: BoardWriteInfo) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        info.checkValidation()
        val boardWrite = setBoardWrite(info, userId)

        client
            .insertBoard(boardWrite)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    private suspend fun setBoardWrite(info: BoardWriteInfo, userId: Int): BoardWrite {
        return if (info.image == null) {
            BoardWrite(
                contents = info.contents,
                image = "",
                teamId = info.teamId,
                userId = userId,
                boardId = info.boardId
            )
        } else {
            val imageAddress = firebaseClient.basicFileUpload(
                fileName = "board/${System.currentTimeMillis()}.png",
                uri = info.image
            ).getOrThrow()

            BoardWrite(
                contents = info.contents,
                image = imageAddress,
                teamId = info.teamId,
                userId = userId,
                boardId = info.boardId
            )
        }
    }

    override fun fetchBoardList(
        skip: Int,
        limit: Int,
        teamId: Int
    ) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .fetchBoardList(
                BoardSearch(
                    skip = skip,
                    limit = limit,
                    teamId = teamId,
                    userId = userId
                )
            )
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun fetchBoardDetail(boardId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .fetchBoardDetail(BoardIdInfoParam(boardId = boardId, userId = userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun updateBoard(info: BoardWriteInfo, isImageChanged: Boolean) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        info.checkValidation()
        val imageAddress =
            if (isImageChanged && info.image != null) {
                firebaseClient.basicFileUpload(
                    fileName = "board/${System.currentTimeMillis()}.png",
                    uri = info.image
                ).getOrThrow()
            } else if (info.image == null) {
                ""
            } else {
                info.image.path ?: ""
            }

        val modifyItem = info.toModify(imageAddress, userId)

        client
            .updateBoard(modifyItem)
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun deleteBoard(boardId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .deleteBoard(BoardIdInfoParam(boardId = boardId, userId = userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }

    override fun insertComment(contents: String, boardId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .insertComment(
                CommentWrite(
                    contents = contents,
                    boardId = boardId,
                    userId = userId
                )
            )
            .onSuccess {
                emit(it)
            }
            .onFailure { throw it }
    }

    override fun deleteComment(commentId: Int, boardId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .deleteComment(
                CommentDelete(
                    commentId = commentId,
                    boardId = boardId,
                    userId = userId
                )
            )
            .onSuccess { emit(it)  }
            .onFailure { throw it }
    }

    override fun updateBoardLike(boardId: Int) = flow {
        val userId = databaseRepository.getUserId()
        if (userId == 0) throw Exception("유저 정보가 없습니다.")

        client
            .updateBoardLike(LikeUpdate(boardId, userId))
            .onSuccess { emit(it) }
            .onFailure { throw it }
    }
}