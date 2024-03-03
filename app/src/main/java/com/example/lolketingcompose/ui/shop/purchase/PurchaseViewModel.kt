package com.example.lolketingcompose.ui.shop.purchase

import androidx.lifecycle.SavedStateHandle
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import com.example.network.repository.PurchaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PurchaseViewModel @Inject constructor(
    private val repository: PurchaseRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    init {
        savedStateHandle.get<Array<Int>>(Constants.GoodsIds)?.let {
            updateMessage("$it")
        } ?: updateMessage("값이 없습니다.")
    }
}