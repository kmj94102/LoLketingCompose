package com.example.lolketingcompose.structure

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel: ViewModel() {

    private val _status = MutableStateFlow(BaseStatus())
    val status: StateFlow<BaseStatus> = _status

    protected fun startLoading() {
        _status.value.startLoading()
    }

    protected fun endLoading() {
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

}