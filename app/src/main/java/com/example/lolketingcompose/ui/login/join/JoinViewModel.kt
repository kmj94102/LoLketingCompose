package com.example.lolketingcompose.ui.login.join

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.auth.model.UserInfo
import com.example.auth.model.UserInfoType
import com.example.auth.repository.AuthRepository
import com.example.lolketingcompose.navigation.NavScreen
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.lolketingcompose.util.getArgumentDecode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val type = savedStateHandle.get<String>(NavScreen.Join.Type).orEmpty()
    private val _isEmail = mutableStateOf(type == UserInfoType.Email.type)
    val isEmail: State<Boolean> = _isEmail

    private val _info = mutableStateOf(UserInfo.create())
    val info: State<UserInfo> = _info

    val address = savedStateHandle.getStateFlow(Constants.Address, "")

    init {
        if (type.isEmpty()) {
            updateMessage("오류가 발생하였습니다.")
            updateFinish(true)
        }
        savedStateHandle.getArgumentDecode<UserInfo>(NavScreen.Join.Data)?.let {
        }

    }

    fun updateAddress(address: String) {
        _info.value = _info.value.copy(address = address)
    }

}