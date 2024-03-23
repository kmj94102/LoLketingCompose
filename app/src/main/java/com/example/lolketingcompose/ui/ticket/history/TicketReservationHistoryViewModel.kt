package com.example.lolketingcompose.ui.ticket.history

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.model.TicketIdParam
import com.example.network.model.TicketInfo
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketReservationHistoryViewModel @Inject constructor(
    private val repository: PurchaseRepository,
    private val savedStateHandle: SavedStateHandle
): BaseViewModel() {
    private val _ticketInfo = mutableStateOf(TicketInfo.init())
    val ticketInfo: State<TicketInfo> = _ticketInfo

    private val _noRefund = mutableStateOf(false)
    val noRefund: State<Boolean> = _noRefund

    private val idList = mutableListOf<Int>()

    init {
        fetchTicketInfo()
    }

    private fun fetchTicketInfo() {
        idList.addAll(
            runCatching {
                savedStateHandle
                    .get<String>(Constants.GameId)
                    ?.split(",")
                    ?.map { it.toInt() }
                    ?: throw Exception()
            }.getOrNull() ?: run {
                updateMessage("티켓 정보가 없습니다.")
                updateFinish()
                return
            }
        )

        repository
            .fetchTicketInfo(
                TicketIdParam(idList)
            )
            .setLoadingState()
            .onEach {
                _ticketInfo.value = it
            }
            .catch {
                updateMessage("티켓 정보가 없습니다.")
                updateFinish()
            }
            .launchIn(viewModelScope)
    }

    fun refundTicket() {
        repository
            .refundTicket(idList)
            .setLoadingState()
            .onEach {
                updateMessage("환불을 완료하였습니다.")
                updateFinish()
            }
            .catch { _noRefund.value = true }
            .launchIn(viewModelScope)
    }

    fun dismiss() {
        _noRefund.value = false
    }
}