package com.example.lolketingcompose.ui.event.roulette

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RouletteViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _count = mutableIntStateOf(20)
    val count: State<Int> = _count

    private val _deg = mutableFloatStateOf(0f)
    val deg: State<Float> = _deg

    private val _resultMessage = mutableStateOf("")
    val resultMessage: State<String> = _resultMessage

    private val result
        get() = when (_deg.floatValue) {
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
        fetchRouletteCount()
    }

    private fun fetchRouletteCount() {
        repository
            .fetchRouletteCount()
            .setLoadingState()
            .onEach {
                _count.intValue = it.count
            }
            .catch {
                updateMessage("오류가 발생하였습니다. 잠시 후 이용해주세요")
                updateFinish()
            }
            .launchIn(viewModelScope)
    }

    fun rouletteStart() = viewModelScope.launch {
        _deg.floatValue = (1..359).random().toFloat()

        repository
            .insertRouletteCoupon(result)
            .onSuccess {
                _count.intValue = it.count
                _resultMessage.value = "${result}RP 쿠폰 당첨!!"
            }
            .onFailure { error ->
                _resultMessage.value = error.message ?: "룰렛에 오류가 발생하였습니다."
            }
    }

}