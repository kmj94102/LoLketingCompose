package com.example.lolketingcompose.ui.mypage.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
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

    private val _list = mutableStateListOf<PurchaseHistoryInfo>()
    val list: List<PurchaseHistoryInfo> = _list

    fun updateSelector(isTicket: Boolean) {
        _isTicket.value = isTicket
    }

    fun fetchPurchaseHistory() {
        repository
            .fetchTicketHistory()
            .onEach {
                _list.clear()
                _list.addAll(it)
            }
            .catch { updateMessage(it.message ?: "구매 정보를 찾을 수 없습니다.") }
            .launchIn(viewModelScope)
    }

}