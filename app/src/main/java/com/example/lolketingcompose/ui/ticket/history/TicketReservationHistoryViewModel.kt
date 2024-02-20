package com.example.lolketingcompose.ui.ticket.history

import androidx.lifecycle.SavedStateHandle
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TicketReservationHistoryViewModel @Inject constructor(
    private val repository: PurchaseRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

}