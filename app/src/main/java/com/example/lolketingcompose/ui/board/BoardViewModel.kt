package com.example.lolketingcompose.ui.board

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.Board
import com.example.network.model.Team
import com.example.network.repository.BoardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val repository: BoardRepository
): BaseViewModel() {

    private val _selectTeam = mutableStateOf(Team.ALL)
    val selectTeam: State<Team> = _selectTeam

    private val _list = mutableStateListOf<Board>()
    val list: List<Board> = _list

    init {
        fetchBoardList()
    }

    fun updateSelectTeam(team: Team) {
        _selectTeam.value = team
    }

    private fun fetchBoardList() {
        repository
            .fetchBoardList(skip = 0, limit = 10)
            .setLoadingState()
            .onEach { _list.addAll(it) }
            .catch { updateMessage(it.message ?: "조회 실패") }
            .launchIn(viewModelScope)
    }

    fun updateBoardLike(boardId: Int) {
        repository
            .updateBoardLike(boardId)
            .setLoadingState()
            .onEach { board ->
                runCatching {
                    val index = _list.indexOfFirst { it.id == boardId  }
                    if (index != -1) {
                        _list[index] = board
                    }
                }
            }
            .catch { updateMessage(it.message ?: "오류 발생") }
            .launchIn(viewModelScope)
    }

}