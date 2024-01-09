package com.example.auth.exception

fun Throwable.checkNetworkError() = message?.uppercase()?.contains("HTTP") == true

object NetworkError : Throwable()

fun <T> Result<T>.getFailureThrow() =
    onFailure {
        it.printStackTrace()
        throw if (it.checkNetworkError()) NetworkError else it
    }

fun <T> Result<T>.printStackTrace() = onFailure { it.printStackTrace() }