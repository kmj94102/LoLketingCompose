package com.example.lolketingcompose.ui.chatting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.clearAndAddAll
import com.example.lolketingcompose.util.getToday
import com.example.network.model.ChattingRoomInfo
import com.example.network.repository.ChattingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChattingListViewModel @Inject constructor(
    private val repository: ChattingRepository
) : BaseViewModel() {

    private val _selectedDate = mutableStateOf(getToday())
    val selectedDate: State<String> = _selectedDate

    private val _list = mutableStateListOf<ChattingRoomInfo>()
    val list: List<ChattingRoomInfo> = _list

    init {
        fetchChattingList()
    }

    private fun fetchChattingList() {
        repository
            .fetchChattingList(_selectedDate.value)
            .setLoadingState()
            .onEach {
                _list.clearAndAddAll(it)
            }
            .catch {
                it.printStackTrace()
                _list.clear()
            }
            .launchIn(viewModelScope)
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
        fetchChattingList()
    }
}