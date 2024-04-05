package com.example.lolketingcompose.ui.board.write

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
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
    private val repository: BoardRepository
) : BaseViewModel() {
    private val _info = mutableStateOf(BoardWriteInfo.init())
    val info: State<BoardWriteInfo> = _info

    fun updateContents(value: String) {
        _info.value = _info.value.copy(contents = value)
    }

    fun updateImage(uri: Uri?) {
        _info.value = _info.value.copy(image = uri)
    }

    fun selectTeam(teamName: String) {
        _info.value = _info.value.copy(
            teamId = Team.getTeamId(teamName),
            teamName = teamName
        )
    }

    fun insertBoard() {
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
}