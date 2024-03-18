package com.example.lolketingcompose.ui.shop.cart

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.database.DatabaseRepository
import com.example.database.GoodsEntity
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.clearAndAddAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : BaseViewModel() {

    private val _list = mutableStateListOf<GoodsEntity>()
    val list: List<GoodsEntity> = _list

    val isAllChecked
        get() = list.all { it.isChecked }

    val totalPrice
        get() = list
            .filter { it.isChecked }
            .map { it.price * it.amount }
            .reduceOrNull { acc, i -> acc + i }
            ?: 0

    init {
        fetchCartList()
    }

    private fun fetchCartList() {
        repository
            .fetchCartList()
            .onEach { _list.clearAndAddAll(it) }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    fun updateCheckedStatus(
        index: Int,
        isChecked: Boolean
    ) = viewModelScope.launch {
        repository.updateCheckedStatus(index, isChecked)
    }

    fun updateCheckedStatusAll() = viewModelScope.launch {
        repository.updateCheckedStatusAll(isAllChecked.not())
    }

    fun updateAmount(index: Int, amount: Int) = viewModelScope.launch {
        if (amount < 1) {
            updateMessage("최소 수량은 1개입니다.")
            return@launch
        } else if (amount > 10) {
            updateMessage("최대 수량은 10개입니다.")
            return@launch
        }
        repository.updateAmount(index, amount)
    }

    fun deleteItems() = viewModelScope.launch {
        repository.deleteItems(_list.filter { it.isChecked })
    }

    fun getSelectedList(): List<GoodsEntity> {
        val selectList = _list.filter { it.isChecked }
        if (selectList.isEmpty()) {
            updateMessage("구매할 상품을 선택해 주세요")
        }
        return selectList
    }

}