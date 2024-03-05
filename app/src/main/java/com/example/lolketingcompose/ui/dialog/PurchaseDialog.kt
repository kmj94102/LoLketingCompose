package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle22B

@Composable
fun PurchaseDialog(
    isShow: Boolean,
    totalPrice: Int,
    myCash: Int,
    onDismiss: () -> Unit,
    okClick: () -> Unit
) {
    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        title = {
            Text(
                text = "굿즈 결제",
                style = textStyle22B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        contents = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Row {
                    Text(
                        text = "총 합계",
                        style = textStyle16(),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = totalPrice.formatWithComma().plus("원"),
                        style = textStyle16B(color = MainColor),
                        modifier = Modifier
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    Text(
                        text = "My 캐시",
                        style = textStyle16(),
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = myCash.formatWithComma().plus("원"),
                        style = textStyle16B(color = MainColor),
                        modifier = Modifier
                    )
                }
            }
        },
        okClick = {
            okClick()
            onDismiss()
        },
        okText = "결제하기"
    )
}