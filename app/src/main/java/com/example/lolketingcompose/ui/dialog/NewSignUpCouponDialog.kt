package com.example.lolketingcompose.ui.dialog

import androidx.compose.runtime.Composable

@Composable
fun NewSignUpCouponDialog(
    isShow: Boolean,
    isIssued: Boolean,
    onDismiss: () -> Unit,
    listener: () -> Unit
) {
    if (isIssued) {
        ConfirmDialog(
            isShow = isShow,
            title = "",
            content = "이미 쿠폰을 발급받으셨습니다.\n내 정보에서 쿠폰을 확인해주세요.",
            onDismiss = onDismiss,
            isSingleButton = true,
            okClick = onDismiss
        )
    } else {
        ConfirmDialog(
            isShow = isShow,
            title = "신규 가입 쿠폰 발급 완료",
            content = "내 정보에서 발급받은 쿠폰을\n확인할 수 있습니다.\n바로 이동하시겠습니까?",
            onDismiss = onDismiss,
            okClick = {
                listener()
                onDismiss()
            }
        )
    }
}