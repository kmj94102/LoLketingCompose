package com.example.lolketingcompose.ui.shop.purchase

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.database.GoodsEntity
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.lolketingcompose.util.clearAndAddAll
import com.example.lolketingcompose.util.getArgumentDecode
import com.example.network.model.PurchaseInfo
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val repository: PurchaseRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _list = mutableStateListOf<GoodsEntity>()
    val list: List<GoodsEntity> = _list

    private val _purchaseInfo = mutableStateOf(PurchaseInfo.init())
    val purchaseInfo: State<PurchaseInfo> = _purchaseInfo

    private val _purchaseStatus = mutableStateOf<PurchaseStatus>(PurchaseStatus.Init)
    val purchaseStatus: State<PurchaseStatus> = _purchaseStatus

    val totalPrice
        get() = _list.map { it.price * it.amount }.reduceOrNull { acc, i -> acc + i } ?: 0

    init {
        savedStateHandle
            .getArgumentDecode<List<GoodsEntity>>(Constants.PurchaseData)
            ?.let {
                _list.clearAndAddAll(it)
            }
            ?: run {
                updateMessage("오류가 발생하였습니다.")
                updateFinish()
            }
        fetchPurchaseInfo()
    }

    private fun fetchPurchaseInfo() {
        repository
            .fetchPurchaseInfo()
            .setLoadingState()
            .onEach { _purchaseInfo.value = it }
            .catch { updateMessage("정보 조회를 실패하였습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateAddress(address: String) {
        _purchaseInfo.value = _purchaseInfo.value.copy(address = address)
    }

    fun updateAmount(index: Int, amount: Int) {
        if (amount < 1) {
            updateMessage("최소 수량은 1개입니다.")
            return
        } else if (amount > 10) {
            updateMessage("최대 수량은 10개입니다.")
            return
        }

        _list[index] = _list[index].copy(amount = amount)
    }

    fun deleteItem(index: Int) {
        _list.removeAt(index)
    }

    fun cashCharging(amount: Int) {
        repository
            .cashCharging(amount)
            .setLoadingState()
            .onEach {
                _purchaseInfo.value = _purchaseInfo.value.copy(cash = it)
            }
            .catch { updateMessage(it.message ?: "캐시 충전에 실패하였습니다. 잠시 후 이용해 주세요.") }
            .launchIn(viewModelScope)
    }

    fun checkPurchase() = viewModelScope.launch {
        if (_purchaseInfo.value.checkValidation().not()) {
            updateMessage("배송 정보를 입력해주세요")
        } else if (_purchaseInfo.value.cash < totalPrice) {
            _purchaseStatus.value = PurchaseStatus.NeedCashCharging
        } else {
            _purchaseStatus.value = PurchaseStatus.Purchase
        }

        delay(500)
        _purchaseStatus.value = PurchaseStatus.Init
    }

    fun purchase() {
       repository
           .insertProductPurchase(_list)
           .setLoadingState()
           .onEach {
               updateMessage("구매 완료")
               updateFinish()
           }
           .catch { updateMessage(it.message ?: "결제 실패") }
           .launchIn(viewModelScope)
    }

    sealed class PurchaseStatus {
        object Init: PurchaseStatus()

        object NeedCashCharging: PurchaseStatus()

        object Purchase: PurchaseStatus()
    }

}