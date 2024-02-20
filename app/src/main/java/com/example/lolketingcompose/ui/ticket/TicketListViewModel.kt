package com.example.lolketingcompose.ui.ticket

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.Game
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketListViewModel @Inject constructor(
    private val repository: PurchaseRepository
): BaseViewModel() {

    private val _list = mutableStateListOf<Game>()
    val list: List<Game> = _list

    fun fetchGameList() {
        repository.fetchGameList()
            .setLoadingState()
            .onEach {
                _list.clear()
                _list.addAll(it)
            }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

}