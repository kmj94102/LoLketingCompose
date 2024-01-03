package com.example.lolketingcompose.ui.home.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.LoginInfo
import com.example.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _info = mutableStateOf(LoginInfo("", ""))
    val info: State<LoginInfo> = _info

    fun updateId(id: String) {
        _info.value = _info.value.copy(id = id)
    }

    fun updatePw(password: String) {
        _info.value = _info.value.copy(password = password)
    }

}