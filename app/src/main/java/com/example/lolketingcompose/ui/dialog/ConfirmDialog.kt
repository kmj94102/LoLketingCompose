package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle22B

@Composable
fun ConfirmDialog(
    isShow: Boolean,
    title: String = "",
    content: String = "",
    cancelable: Boolean = true,
    onDismiss: () -> Unit,
    isSingleButton: Boolean = false,
    okText: String = "확인",
    cancelText: String = "취소",
    okClick: () -> Unit,
    cancelClick: () -> Unit = onDismiss,
) {
    CommonConfirmDialog(
        isShow = isShow,
        title = if (title.isNotEmpty()) {
            {
                Text(
                    text = title,
                    style = textStyle22B(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        } else null,
        contents = {
            Text(
                text = content,
                style = textStyle16(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        cancelable = cancelable,
        onDismiss = onDismiss,
        isSingleButton = isSingleButton,
        okText = okText,
        cancelText = cancelText,
        okClick = okClick,
        cancelClick = cancelClick
    )
}