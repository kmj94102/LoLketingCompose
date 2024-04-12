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
    val commentCount: Int,
    val isAuthor: Boolean
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

data class BoardIdInfoParam(
    val boardId: Int,
    val userId: Int,
)

data class BoardDetail(
    val id: Int,
    val contents: String,
    val image: String,
    val timestamp: String,
    val name: String,
    val nickname: String,
    val likeCount: Int,
    val isLike: Boolean,
    val commentList: List<Comment>,
    val isAuthor: Boolean
) {
    fun toBoard() = Board(
        id = id,
        contents = contents,
        image = image,
        timestamp = timestamp,
        name = name,
        nickname = nickname,
        likeCount = likeCount,
        isLike = isLike,
        commentCount = commentList.size,
        isAuthor = isAuthor
    )

    companion object {
        fun init() = BoardDetail(
            id = 0,
            contents = "",
            image = "",
            timestamp = "",
            name = "",
            nickname = "",
            likeCount = 0,
            isLike = false,
            commentList = listOf(),
            isAuthor = false
        )
    }
}

data class Comment(
    val commentId: Int,
    val contents: String,
    val timestamp: String,
    val userId: Int,
    val nickname: String,
    val boardId: Int,
    val isAuthor: Boolean
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