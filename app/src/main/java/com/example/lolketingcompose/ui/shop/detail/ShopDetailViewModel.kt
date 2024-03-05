package com.example.lolketingcompose.ui.shop.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.database.DatabaseRepository
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.model.GoodsDetail
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShopDetailViewModel @Inject constructor(
    private val repository: PurchaseRepository,
    private val databaseRepository: DatabaseRepository,
    val savedStateHandle: SavedStateHandle
): BaseViewModel() {

    private val _item = mutableStateOf(GoodsDetail.init())
    val item: State<GoodsDetail> = _item

    private val _amount = mutableIntStateOf(1)
    val amount: State<Int> = _amount

    val cartCount = databaseRepository
        .fetchCartCount()
        .stateIn(viewModelScope, started = SharingStarted.Lazily, initialValue = 0)

    val entity
        get() = _item.value.toEntity(_amount.intValue)

    init {
        fetchGoodsItemDetail()
    }

    private fun fetchGoodsItemDetail() {
        val goodsId = savedStateHandle
            .get<Int>(Constants.GoodsId)
            ?: run {
                updateMessage("상품 정보가 없습니다.")
                updateFinish()
                return
            }

        repository
            .fetchGoodsItemDetail(goodsId = goodsId)
            .onEach { _item.value = it }
            .catch { updateMessage("상품 정보가 없습니다.") }
            .launchIn(viewModelScope)
    }

    fun updateAmount(value: Int) {
        when(val newValue = _amount.intValue + value) {
            0 -> {
                _amount.intValue = 1
                updateMessage("최소 수량은 1개입니다.")
            }
            in 1..10 -> {
                _amount.intValue = newValue
            }
            else -> {
                _amount.intValue = 10
                updateMessage("최대 수량은 10개입니다.")
            }
        }
    }

    fun insertCart(onSuccess: () -> Unit) = viewModelScope.launch {
        databaseRepository
            .insertGoods(entity)
            .onSuccess { onSuccess() }
            .onFailure { updateMessage("실패") }
    }
}