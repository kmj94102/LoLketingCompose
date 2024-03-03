package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B

@Composable
fun CartAddSuccessDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    okClick: () -> Unit,
    cancelClick: () -> Unit
) {
    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        contents = {
            Text(
                text = "장바구니에 상품을 추가하였습니다.",
                style = textStyle16(color = MyLightGray, textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        cancelClick = {
            cancelClick()
            onDismiss()
        },
        cancelText = "장바구니 보기",
        cancelTextStyle = textStyle16B(),
        okClick = {
            okClick()
            onDismiss()
        },
        okText = "계속 쇼핑하기",
        okTextStyle = textStyle16B(),
    )
}