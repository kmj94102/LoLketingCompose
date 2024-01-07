package com.example.lolketingcompose.structure

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class BaseStatus {
    var message by mutableStateOf(Any())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isNetworkError by mutableStateOf(false)
        private set

    var isFinish by mutableStateOf(false)
        private set

    fun updateMessage(message: String) {
        this.message = message
    }

    fun startLoading() {
        isLoading = true
        isNetworkError = false
    }

    fun endLoading() {
        isLoading = false
    }

    fun updateNetworkErrorState(value: Boolean) {
        isNetworkError = value
    }

    fun updateFinish(value: Boolean) {
        isFinish = value
    }
}