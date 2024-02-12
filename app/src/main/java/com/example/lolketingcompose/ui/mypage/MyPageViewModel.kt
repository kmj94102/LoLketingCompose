package com.example.lolketingcompose.ui.mypage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.auth.repository.AuthRepository
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.MyInfo
import com.example.network.model.UpdateCashItem
import com.example.network.model.UpdateCouponItem
import com.example.network.repository.MainRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val mainRepository: MainRepositoryImpl,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _myInfo = mutableStateOf(MyInfo.init())
    val myInfo: State<MyInfo> = _myInfo

    private val _isLogout = mutableStateOf(false)
    val isLogout: State<Boolean> = _isLogout

    fun fetchMyInfo() {
        mainRepository
            .fetchMyInfo()
            .setLoadingState()
            .onEach { _myInfo.value = it }
            .catch { updateMessage(it.message ?: "유저 정보가 없습니다.") }
            .launchIn(viewModelScope)
    }

    fun logout() = viewModelScope.launch {
        authRepository
            .logout()
            .onSuccess { _isLogout.value = true }
            .onFailure { updateMessage(it.message ?: "로그아웃 실패") }
    }

    fun withdrawal() = viewModelScope.launch {
        authRepository
            .withdrawal()
            .onSuccess { _isLogout.value = true }
            .onFailure { updateMessage(it.message ?: "회원 탈퇴 실패") }
    }

    fun updateCashCharging(cash: Int) {
        mainRepository
            .updateCashCharging(
                UpdateCashItem(
                    id = _myInfo.value.id,
                    cash = cash
                )
            )
            .setLoadingState()
            .onEach {
                _myInfo.value = it
            }
            .catch { updateMessage(it.message ?: "충전 중 오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateUsingCoupon(id: Int) {
        mainRepository
            .updateUsingCoupon(
                UpdateCouponItem(
                    id = _myInfo.value.id,
                    couponId = id
                )
            )
            .setLoadingState()
            .onEach {
                _myInfo.value = it
            }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

}