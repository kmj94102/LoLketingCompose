package com.example.lolketingcompose.ui.home

import com.example.auth.repository.AuthRepository
import com.example.lolketingcompose.structure.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository
): BaseViewModel() {

    suspend fun isLogin() = repository.isLogin()

}