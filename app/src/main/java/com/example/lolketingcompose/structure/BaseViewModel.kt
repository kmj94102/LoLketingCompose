package com.example.lolketingcompose.structure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel: ViewModel() {

    private val _status = MutableStateFlow(BaseStatus())
    val status: StateFlow<BaseStatus> = _status

    private fun startLoading() {
        _status.value.startLoading()
    }

    private fun endLoading() {
        _status.value.endLoading()
    }

    protected fun updateMessage(message: String) {
        _status.value.updateMessage(message)
    }

    protected fun updateNetworkErrorState(value: Boolean = true) {
        _status.value.updateNetworkErrorState(value)
    }

    protected fun updateFinish(value: Boolean = true) {
        _status.value.updateFinish(value)
    }

    fun <T> Flow<T>.setLoadingState(): Flow<T> =
        onStart { startLoading() }.onCompletion { endLoading() }

}