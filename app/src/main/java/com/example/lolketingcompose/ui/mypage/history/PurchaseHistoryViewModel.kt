package com.example.lolketingcompose.ui.mypage.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.clearAndAddAll
import com.example.network.model.PurchaseHistoryInfo
import com.example.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PurchaseHistoryViewModel @Inject constructor(
    private val repository: MainRepository
): BaseViewModel() {

    private val _isTicket = mutableStateOf(true)
    val isTicket: State<Boolean> = _isTicket

    private val _ticketList = mutableStateListOf<PurchaseHistoryInfo>()
    val ticketList: List<PurchaseHistoryInfo> = _ticketList

    private val _goodsList = mutableStateListOf<PurchaseHistoryInfo>()
    val goodsList: List<PurchaseHistoryInfo> = _goodsList

    fun updateSelector(isTicket: Boolean) {
        _isTicket.value = isTicket
    }

    fun fetchPurchaseHistory() {
        repository
            .fetchTicketHistory()
            .onEach { _ticketList.clearAndAddAll(it) }
            .catch { updateMessage(it.message ?: "구매 정보를 찾을 수 없습니다.") }
            .launchIn(viewModelScope)
    }

    fun fetchGoodsHistory() {
        repository
            .fetchGoodsHistory()
            .onEach { _goodsList.clearAndAddAll(it) }
            .catch { updateMessage(it.message ?: "구매 정보를 찾을 수 없습니다.") }
            .launchIn(viewModelScope)
    }

}