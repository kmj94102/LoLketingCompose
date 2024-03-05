package com.example.lolketingcompose.ui.ticket.reservation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.model.ReservationInfo
import com.example.network.model.ReservationTicketItem
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TicketReservationViewModel @Inject constructor(
    private val repository: PurchaseRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val gameId = savedStateHandle.get<Int>(Constants.GameId)
    val ticketPrice = 11_000

    private val _numberOfPeople = mutableStateOf(2)
    val numberOfPeople: State<Int> = _numberOfPeople

    private val _reservation = mutableStateOf<Reservation>(Reservation.Init)
    val reservation: State<Reservation> = _reservation

    private val _ticketInfo = mutableStateOf(ReservationInfo.init())
    val ticketInfo: State<ReservationInfo> = _ticketInfo
    val list
        get() = _ticketInfo.value.getSeatList()

    val selectedSeatInfo
        get() = runCatching {
            _ticketInfo.value.selectedSeats.reduce { acc, s -> "$acc, $s" }
        }.getOrElse { "-" }

    fun fetchReservedSeats() {
        if (gameId == null) {
            updateMessage("경기 정보가 없습니다.")
            updateFinish()
            return
        }

        repository
            .fetchReservedSeats(gameId)
            .setLoadingState()
            .onEach {
                _ticketInfo.value = _ticketInfo.value.setItem(it)
            }
            .catch { error -> error.message?.let { updateMessage(it) } }
            .launchIn(viewModelScope)
    }

    fun onSeatClick(number: String) {
        val selectedSeats = _ticketInfo.value.selectedSeats.toMutableList()
        if (_ticketInfo.value.reservedSeats.contains(number)) {
            updateMessage("예매된 좌석입니다.")
            return
        }

        if (selectedSeats.contains(number)) {
            selectedSeats.remove(number)
            _ticketInfo.value = _ticketInfo.value.copy(selectedSeats = selectedSeats)
            return
        }

        if (selectedSeats.size >= _numberOfPeople.value) {
            updateMessage("선택한 인원수를 확인해 주세요.")
            return
        }

        selectedSeats.add(number)
        selectedSeats.sort()
        _ticketInfo.value = _ticketInfo.value.copy(selectedSeats = selectedSeats)
    }

    fun updateNumberOfPeople(number: Int) {
        _numberOfPeople.value = number
        _ticketInfo.value = _ticketInfo.value.copy(
            selectedSeats = listOf()
        )
    }

    fun makeReservation() {
        val ticketInfo = _ticketInfo.value
        val numberOfPeople = _numberOfPeople.value
        if (ticketPrice * numberOfPeople > ticketInfo.cash) {
            _reservation.value = Reservation.CashCharging
            return
        }

        repository
            .reservationTicket(
                ReservationTicketItem(
                    gameId = ticketInfo.gameId,
                    userId = ticketInfo.userId,
                    count = numberOfPeople,
                    seatNumber = selectedSeatInfo.replace(" ","")
                )
            )
            .onEach {
                _reservation.value = Reservation.Success(it)
            }
            .catch {
                updateMessage(it.message ?: "티켓 예매에 실패하였습니다.")
            }
            .launchIn(viewModelScope)
    }

    fun cashCharging(cash: Int) {
        if (gameId == null) {
            updateMessage("경기 정보가 없습니다.")
            return
        }

        repository
            .cashChargingAndReservationInfo(cash = cash, gameId = gameId)
            .onEach {
                _ticketInfo.value = _ticketInfo.value.setItem(it)
            }
            .catch { error -> error.message?.let { updateMessage(it) } }
            .launchIn(viewModelScope)
    }

    fun updateInit() {
        _reservation.value = Reservation.Init
    }

    sealed class Reservation {
        object Init: Reservation()

        object CashCharging: Reservation()

        data class Success(
            val ids: String
        ) : Reservation()
    }

}