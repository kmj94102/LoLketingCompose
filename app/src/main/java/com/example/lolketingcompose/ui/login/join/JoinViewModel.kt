package com.example.lolketingcompose.ui.login.join

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.auth.model.JoinInfo
import com.example.auth.model.UserInfoType
import com.example.auth.repository.AuthRepository
import com.example.lolketingcompose.R
import com.example.lolketingcompose.navigation.NavScreen
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.getArgumentDecode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class JoinViewModel @Inject constructor(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val type = savedStateHandle.get<String>(NavScreen.Join.Type).orEmpty()
    private val _isEmail = mutableStateOf(type == UserInfoType.Email.type)
    val isEmail: State<Boolean> = _isEmail

    private val _info = mutableStateOf(JoinInfo.create())
    val info: State<JoinInfo> = _info

    private val _readOnly = mutableStateOf(false)
    val readOnly: State<Boolean> =_readOnly

    private val _guide = mutableStateOf("")
    val guide: State<String> = _guide

    private val _complete = mutableStateOf(false)
    val complete: State<Boolean> = _complete


    init {
        if (type.isEmpty()) {
            updateMessage("오류가 발생하였습니다.")
            updateFinish()
        }
        savedStateHandle.getArgumentDecode<JoinInfo>(NavScreen.Join.Data)?.let {
            _info.value = it
            if (it.id.trim().isNotEmpty()) {
                _readOnly.value = true
            }
        }
    }

    fun updateField(updateValue: () -> JoinInfo) {
        _info.value = updateValue()
    }

    fun updateAddress(address: String) {
        _info.value = _info.value.copy(address = address)
    }

    fun join() {
        repository.join(_info.value)
            .setLoadingState()
            .onEach {
                updateMessage(R.string.join_success)
                _complete.value = true
            }
            .catch {
                updateMessage(it.message ?: "회원가입 실패")
            }
            .launchIn(viewModelScope)
    }

    fun updateGuide(msg: String) {
        _guide.value = msg
    }

}