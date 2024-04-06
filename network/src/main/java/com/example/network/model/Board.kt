package com.example.network.model

import android.net.Uri

data class Board(
    val id: Int,
    val contents: String,
    val image: String,
    val timestamp: String,
    val name: String,
    val nickname: String,
    val likeCount: Int,
    val isLike: Boolean,
    val commentCount: Int
)

data class BoardWrite(
    val contents: String,
    val image: String,
    val teamId: Int,
    val userId: Int
)

data class BoardWriteInfo(
    val contents: String,
    val image: Uri?,
    val teamId: Int,
    val teamName: String
) {
    fun checkValidation() = when {
        contents.isEmpty() -> throw Exception("내용을 입력해 주세요.")
        teamId == 0 -> throw Exception("팀을 선택해 주세요.")
        else -> true
    }

    companion object {
        fun init() = BoardWriteInfo(
            contents = "",
            image = null,
            teamId = 0,
            teamName = ""
        )
    }
}

data class BoardSearch(
    val skip: Int,
    val limit: Int,
    val userId: Int
)

data class Comment(
    val id: Int,
    val contents: String,
    val timestamp: String,
    val userId: Int,
    val boardId: Int
)

data class CommentWrite(
    val contents: String,
    val userId: Int,
    val boardId: Int
)

data class CommentDelete(
    val commentId: Int,
    val boardId: Int,
    val userId: Int
)

data class LikeUpdate(
    val boardId: Int,
    val userId: Int
)