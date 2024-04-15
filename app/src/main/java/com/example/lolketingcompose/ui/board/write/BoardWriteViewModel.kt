package com.example.lolketingcompose.ui.board.write

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.model.BoardWriteInfo
import com.example.network.model.Team
import com.example.network.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BoardWriteViewModel @Inject constructor(
    private val repository: BoardRepository,
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _info = mutableStateOf(BoardWriteInfo.init())
    val info: State<BoardWriteInfo> = _info

    private val boardId = savedStateHandle.get<Int>(Constants.BoardId) ?: 0
    private var isImageChanged = false

    init {
        if (boardId != 0) fetchModifyInfo(boardId)
    }

    private fun fetchModifyInfo(boardId: Int) {
        repository
            .fetchBoardDetail(boardId = boardId)
            .setLoadingState()
            .onEach {
                _info.value = it.toBoardWriteInfo()
            }
            .catch {
                updateMessage(it.message ?: "게시글 정보를 불러오지 못하였습니다.")
                updateFinish()
            }
            .launchIn(viewModelScope)
    }

    fun updateContents(value: String) {
        _info.value = _info.value.copy(contents = value)
    }

    fun updateImage(uri: Uri?) {
        _info.value = _info.value.copy(image = uri)
        isImageChanged = true
    }

    fun selectTeam(team: Team) {
        _info.value = _info.value.copy(
            teamId = team.teamId,
            teamName = team.teamName
        )
    }

    fun register() {
        if (boardId == 0) {
            insertBoard()
        } else {
            updateBoard()
        }
    }

    private fun insertBoard() {
        repository
            .insertBoard(_info.value)
            .setLoadingState()
            .onEach {
                updateMessage(it)
                updateFinish()
            }
            .catch {
                updateMessage(it.message ?: "게시글 등록 실패")
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }

    private fun updateBoard() {
        repository
            .updateBoard(_info.value, isImageChanged)
            .setLoadingState()
            .onEach {
                updateMessage(it)
                updateFinish()
            }
            .catch {
                updateMessage(it.message ?: "게시글 수정 실패")
                it.printStackTrace()
            }
            .launchIn(viewModelScope)
    }
}