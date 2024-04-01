package com.example.lolketingcompose.ui.chatting.room

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.lolketingcompose.util.clearAndAddAll
import com.example.lolketingcompose.util.getArgumentDecode
import com.example.network.model.ChattingItem
import com.example.network.model.ChattingRoomInfo
import com.example.network.repository.ChattingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel @Inject constructor(
    private val repository: ChattingRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    val roomInfo = savedStateHandle.getArgumentDecode<ChattingRoomInfo>(Constants.RoomInfo)
        ?: ChattingRoomInfo.init()
    private val selectedTeam = savedStateHandle.get<String>(Constants.SelectedTeam)

    private val _list = mutableStateListOf<ChattingItem>()
    val list: List<ChattingItem> = _list

    private val _message = mutableStateOf("")
    val message: State<String> = _message

    init {
        fetchChatting()
    }

    private fun fetchChatting() {
        repository
            .observeChatUpdates(roomInfo)
            .onEach {
                _list.clearAndAddAll(it)
            }
            .catch { updateMessage(it.message ?: "오류 발생")
            it.printStackTrace()}
            .launchIn(viewModelScope)
    }

    fun addChat() {
        if (selectedTeam == null) return
        repository
            .addChat(_message.value, selectedTeam, roomInfo)
            .onEach { _message.value = "" }
            .catch { updateMessage(it.message ?: "채팅 실패") }
            .launchIn(viewModelScope)
    }

    fun updateChattingMessage(message: String) {
        _message.value = message
    }

}