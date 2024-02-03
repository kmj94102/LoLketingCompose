package com.example.lolketingcompose.ui.mypage

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer

@Composable
fun MyPageScreen(
    onBackClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonHeader(
                title = "내 정보",
                onBackClick = onBackClick
            )
        },
        bodyContent = {}
    )
}

@Composable
fun UserInfoContainer(
    modifier: Modifier
) {
    Row(modifier = modifier) {

    }
}