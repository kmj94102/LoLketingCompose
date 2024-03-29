package com.example.lolketingcompose.ui.news

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.network.model.NewsContents
import com.example.network.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
): BaseViewModel() {

    private val _list = mutableStateListOf<NewsContents>()
    val list: List<NewsContents> = _list
    private val _isMore = mutableStateOf(true)
    val isMore: State<Boolean> = _isMore

    private var start = 1
    private var display = 100

    fun fetchNews() {
        repository
            .fetchNews(display = display, start = start)
            .setLoadingState()
            .onEach {
                _list.addAll(it.mapper())
                if (it.total <= start * display || start * display >= 1000) {
                    _isMore.value = false
                } else {
                    start += display
                }
            }
            .catch { it.printStackTrace() }
            .launchIn(viewModelScope)
    }

    init {
        fetchNews()
    }
}