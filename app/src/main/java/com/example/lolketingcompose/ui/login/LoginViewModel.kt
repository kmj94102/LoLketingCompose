package com.example.lolketingcompose.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.auth.repository.AuthRepository
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.LoginInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel() {

    private val _info = mutableStateOf(LoginInfo("", ""))
    val info: State<LoginInfo> = _info

    init {
        fetchNaverProfile()
    }

    fun updateId(id: String) {
        _info.value = _info.value.copy(id = id)
    }

    fun updatePw(password: String) {
        _info.value = _info.value.copy(password = password)
    }

    fun naverLogin(context: Context) {
        repository
            .naverLogin(context)
            .onEach {
                fetchNaverProfile()
            }
            .catch { context.getString(R.string.login_failure) }
            .launchIn(viewModelScope)
    }

    private fun fetchNaverProfile() {
        repository
            .fetchNaverProfile()
            .onEach { Log.e("++++++", it.toString()) }
            .catch {  }
            .launchIn(viewModelScope)
    }

    fun kakaoLogin(context: Context) {
        repository
            .kakaoLogin(context)
            .onEach { fetchKakaoProfile() }
            .catch { context.getString(R.string.login_failure)  }
            .launchIn(viewModelScope)
    }

    private fun fetchKakaoProfile() {
        repository
            .fetchKakaoProfile()
            .onEach {  }
            .catch {  }
            .launchIn(viewModelScope)
    }

}