package com.example.lolketingcompose.ui.board.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.model.BoardDetail
import com.example.network.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BoardDetailViewModel @Inject constructor(
    private val repository: BoardRepository,
    val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val boardId = savedStateHandle.get<Int>(Constants.BoardId)

    private val _info = mutableStateOf(BoardDetail.init())
    val info: State<BoardDetail> = _info

    private val _commentWrite = mutableStateOf("")
    val commentWrite: State<String> = _commentWrite

    fun fetchBoardDetail() {
        if (boardId == null) {
            updateMessage("게시글 정보가 없습니다.")
            updateFinish()
            return
        }

        repository
            .fetchBoardDetail(boardId = boardId)
            .setLoadingState()
            .onEach { _info.value = it }
            .catch { updateMessage(it.message ?: "게시글 정보가 없습니다.") }
            .launchIn(viewModelScope)
    }

    fun deleteBoard(boardId: Int) {
        repository
            .deleteBoard(boardId)
            .onEach { updateFinish() }
            .catch { updateMessage(it.message ?: "삭제 실패") }
            .launchIn(viewModelScope)
    }

    fun insertComment() {
        if (boardId == null) {
            updateMessage("게시글 정보가 없습니다.")
            updateFinish()
            return
        }

        repository
            .insertComment(
                contents = _commentWrite.value,
                boardId = boardId
            )
            .setLoadingState()
            .onEach {
                _commentWrite.value = ""
                _info.value = _info.value.copy(commentList = it)
            }
            .catch { updateMessage(it.message ?: "댓글 등록 실패") }
            .launchIn(viewModelScope)
    }

    fun updateComment(value: String) {
        _commentWrite.value = value
    }

    fun deleteComment(commentId: Int) {
        if (boardId == null) {
            updateMessage("게시글 정보가 없습니다.")
            updateFinish()
            return
        }

        repository
            .deleteComment(commentId = commentId, boardId = boardId)
            .setLoadingState()
            .onEach {
                _info.value = _info.value.copy(commentList = it)
            }
            .catch { updateMessage(it.message ?: "댓글 삭제 실패") }
            .launchIn(viewModelScope)
    }

    fun updateLike(boardId: Int) {
        repository
            .updateBoardLike(boardId)
            .onEach {
                _info.value = _info.value.copy(
                    isLike = it.isLike,
                    likeCount = it.likeCount
                )
            }
            .catch { updateMessage(it.message ?: "좋아요 실패") }
            .launchIn(viewModelScope)
    }

}