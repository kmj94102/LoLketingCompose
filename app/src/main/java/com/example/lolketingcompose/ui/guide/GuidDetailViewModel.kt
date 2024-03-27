package com.example.lolketingcompose.ui.guide

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.lolketingcompose.structure.BaseViewModel
import com.example.lolketingcompose.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GuidDetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _guide = mutableStateOf(Guide.AOS)
    val guide: State<Guide> = _guide

    init {
        savedStateHandle.get<Int>(Constants.Title)?.let {
            _guide.value = Guide.getGuide(it)
        }?: {
            updateMessage("오류가 발생하였습니다.")
            updateFinish()
        }
    }
}