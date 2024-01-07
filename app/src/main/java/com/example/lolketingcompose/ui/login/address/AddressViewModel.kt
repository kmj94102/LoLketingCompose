package com.example.lolketingcompose.ui.login.address

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.AddressSearchInfo
import com.example.network.repository.AddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val repository: AddressRepository
): BaseViewModel() {

    private val _list = mutableStateListOf<String>()
    val list: List<String> = _list

    private val _info = mutableStateOf(AddressSearchInfo())
    val info: State<AddressSearchInfo> = _info

    private var beforeKeyword = ""
    private var currentPage = 1
    private var isMoreData = true

    private fun setIsSearchMode(isSearchMode: Boolean) {
        _info.value = _info.value.copy(isSearchMode = isSearchMode)
    }

    fun setAddress(address: String) {
        _info.value = _info.value.copy(address = address, keyword = address)
        setIsSearchMode(false)
    }

    fun fetchAddressList() {
        if (_info.value.isSearchMode.not()) setIsSearchMode(true)

        if (_info.value.keyword != beforeKeyword) {
            currentPage = 1
            _list.clear()
            beforeKeyword = _info.value.keyword
            isMoreData = true
        }

        if (isMoreData.not()) return

        repository
            .fetchAddress(
                keyword = _info.value.keyword,
                currentPage = currentPage
            )
            .setLoadingState()
            .onEach {
                if (it.list.isEmpty()) {
                    isMoreData = false
                    if (_list.isEmpty()) {
                        updateMessage("입력한 주소를 확인해 주세요.")
                    }
                } else {
                    currentPage++
                    _list.addAll(it.list)
                    isMoreData = it.isMoreData
                }
            }
            .catch {
                updateMessage("입력한 주소를 확인해 주세요.")
            }
            .launchIn(viewModelScope)
    }

    fun updateKeyword(keyword: String) {
       _info.value = _info.value.copy(keyword = keyword)
    }

    fun updateAddressDetail(addressDetail: String) {
        _info.value = _info.value.copy(addressDetail = addressDetail)
    }

}