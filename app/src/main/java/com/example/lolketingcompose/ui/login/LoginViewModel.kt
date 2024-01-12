package com.example.lolketingcompose.ui.login

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.auth.model.LoginInfo
import com.example.auth.model.UserInfo
import com.example.auth.repository.AuthRepository
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
) : BaseViewModel() {

    private val _info = mutableStateOf(LoginInfo("", ""))
    val info: State<LoginInfo> = _info

    private val _loginStatus = mutableStateOf<LoginStatus>(LoginStatus.Init)
    val loginStatus: State<LoginStatus> = _loginStatus

    fun updateId(id: String) {
        _info.value = _info.value.copy(id = id)
    }

    fun updatePw(password: String) {
        _info.value = _info.value.copy(password = password)
    }

    fun emailLogin() {
        repository
            .emailLogin(_info.value)
            .setLoadingState()
            .onEach { _loginStatus.value = LoginStatus.Success }
            .catch {
                it.message?.let { message ->
                    updateMessage(message)
                } ?: updateMessage(R.string.login_failure)
            }
            .launchIn(viewModelScope)
    }

    fun naverLogin(context: Context) {
        repository
            .naverLogin(context)
            .onEach {
                it?.let { info ->
                    _loginStatus.value = LoginStatus.SocialJoin(info)
                } ?: run {
                    _loginStatus.value = LoginStatus.Success
                }
                delay(300)
                _loginStatus.value = LoginStatus.Init
            }
            .catch {
                it.message?.let { message ->
                    updateMessage(message)
                } ?: updateMessage(R.string.login_failure)
            }
            .launchIn(viewModelScope)
    }

    fun kakaoLogin(context: Context) {
        repository
            .kakaoLogin(context)
            .onEach {
                it?.let { info ->
                    _loginStatus.value = LoginStatus.SocialJoin(info)
                } ?: run {
                    _loginStatus.value = LoginStatus.Success
                }
                delay(300)
                _loginStatus.value = LoginStatus.Init
            }
            .catch {
                it.message?.let { message ->
                    updateMessage(message)
                } ?: updateMessage(R.string.login_failure)
            }
            .launchIn(viewModelScope)
    }

    sealed class LoginStatus {
        object Init: LoginStatus()

        object Success: LoginStatus()

        data class SocialJoin(val userInfo: UserInfo): LoginStatus()
    }

}