package com.example.lolketingcompose.ui.dialog

import androidx.compose.runtime.Composable

@Composable
fun RouletteGuideDialog(
    isShow: Boolean,
    onDismiss: () -> Unit
) {
    ConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        content = "룰렛 이용 횟수를 모두 소진하였습니다.\n티켓을 구매하여 룰렛을 더 돌려보세요",
        isSingleButton = true,
        okClick = onDismiss
    )
}