package com.example.lolketingcompose.ui.event.roulette

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val repository: MainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val userId = savedStateHandle.get<Int>(Constants.UserId)

    private val _count = mutableStateOf(20)
    val count: State<Int> = _count

    private val _deg = mutableStateOf(0f)
    val deg: State<Float> = _deg

    private val _resultMessage = mutableStateOf("")
    val resultMessage: State<String> = _resultMessage

    private val result
        get() = when (_deg.value) {
            in 0f..45f -> 2000
            in 46f..90f -> 300
            in 91f..135f -> 350
            in 136f..180f -> 200
            in 181f..225f -> 1000
            in 226f..270f -> 250
            in 271f..315f -> 450
            else -> 250
        }

    init {
        userId?.let(::fetchRouletteCount) ?: {
            updateMessage("오류가 발생하였습니다. 잠시 후 이용해주세요")
            updateFinish()
        }
    }

    private fun fetchRouletteCount(userId: Int) {
        repository
            .fetchRouletteCount(userId)
            .setLoadingState()
            .onEach {
                _count.value = it.count
            }
            .catch {
                updateMessage("오류가 발생하였습니다. 잠시 후 이용해주세요")
                updateFinish()
            }
            .launchIn(viewModelScope)
    }

    fun rouletteStart() = viewModelScope.launch {
        if (_count.value <= 0) {
            updateMessage("룰렛의 기회를 모두 소진하였습니다. 티켓을 구매하시면 룰렛 횟수를 추가로 받아보세요")
            return@launch
        }
        if (userId == null) {
            updateMessage("유저 정보가 없습니다.")
            return@launch
        }
        _deg.value = (1..359).random().toFloat()

        repository
            .insertRouletteCoupon(
                id = userId,
                rp = result
            )
            .onSuccess {
                _count.value = it.count
                _resultMessage.value = "${result}RP 쿠폰 당첨!!"
            }
            .onFailure { error ->
                _resultMessage.value = error.message ?: "룰렛에 오류가 발생하였습니다."
            }
    }

}