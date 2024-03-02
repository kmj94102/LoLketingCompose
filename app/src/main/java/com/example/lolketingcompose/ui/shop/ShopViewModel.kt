package com.example.lolketingcompose.ui.shop

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.clearAndAddAll
import com.example.network.model.Goods
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val repository: PurchaseRepository
): BaseViewModel() {

    val tabList = listOf("전체", "스태츄", "피규어", "인형", "액세서리", "의류")

    private val _selectedIndex = mutableIntStateOf(0)
    val selectedIndex: State<Int> = _selectedIndex

    private val _itemList = mutableStateListOf<Goods>()
    val itemList: List<Goods>
        get() = when(_selectedIndex.intValue) {
            0 -> _itemList
            else -> _itemList.filter { it.category == tabList[_selectedIndex.intValue] }
        }

    init {
        fetchGoodsItems()
    }

    fun updateTabIndex(index: Int) {
        _selectedIndex.intValue = index
    }

    private fun fetchGoodsItems() {
        repository
            .fetchGoodsItems()
            .setLoadingState()
            .onEach {
                _itemList.clearAndAddAll(it)
            }
            .catch { updateMessage(it.message ?: "오류가 발생하였습니다.") }
            .launchIn(viewModelScope)
    }

}