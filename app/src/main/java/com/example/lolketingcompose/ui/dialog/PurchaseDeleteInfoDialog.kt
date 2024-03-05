package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MyRed
import com.example.lolketingcompose.util.textStyle16


@Composable
fun PurchaseDeleteInfoDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    okClick: () -> Unit
) {
    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        contents = {
            Text(
                text = "해당 상품을 삭제하면\n결제를 진행하실 수 없습니다.\n그래도 삭제하시겠습니까?",
                style = textStyle16(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        okClick = {
            okClick()
            onDismiss()
        },
        okButtonColor = MyRed
    )
}