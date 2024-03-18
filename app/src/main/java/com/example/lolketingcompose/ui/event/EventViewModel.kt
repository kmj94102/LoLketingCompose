package com.example.lolketingcompose.ui.event

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val mainRepository: MainRepository
): BaseViewModel() {

    private val _isIssued = mutableStateOf(false)
    val isIssued: State<Boolean> = _isIssued

    private val _isComplete = mutableStateOf(false)
    val isComplete: State<Boolean> = _isComplete

    fun fetchNewUserCoupon() {
        mainRepository
            .fetchNewUserCoupon()
            .setLoadingState()
            .onEach { _isIssued.value = it }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun insertNewUserCoupon() {
        if (_isIssued.value) {
            _isComplete.value = true
            return
        }

        mainRepository
            .insertNewUserCoupon()
            .setLoadingState()
            .onEach {
                _isComplete.value = true
            }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateIsComplete() {
        _isComplete.value = false
    }
}