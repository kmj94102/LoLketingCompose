package com.example.lolketingcompose.ui.mypage.modify

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.ModifyInfo
import com.example.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyInfoModifyViewModel @Inject constructor(
    private val repository: MainRepository
) : BaseViewModel() {

    private val _modifyInfo = mutableStateOf(ModifyInfo.init())
    val modifyInfo: State<ModifyInfo> = _modifyInfo

    init {
        fetchModifyInfo()
    }

    private fun fetchModifyInfo() {
        repository
            .fetchModifyInfo()
            .setLoadingState()
            .onEach { _modifyInfo.value = it }
            .catch {
                updateMessage(it.message ?: "오류가 발생하였습니다")
                updateFinish()
            }
            .launchIn(viewModelScope)
    }

    fun updateInfo(info: ModifyInfo) {
        _modifyInfo.value = info
    }

    fun updateAddress(address: String) {
        _modifyInfo.value = _modifyInfo.value.copy(address = address)
    }

    fun updateMyInfo() {
        repository
            .updateMyInfo(_modifyInfo.value)
            .setLoadingState()
            .onEach {
                updateMessage("업데이트 완료")
                updateFinish()
            }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

}