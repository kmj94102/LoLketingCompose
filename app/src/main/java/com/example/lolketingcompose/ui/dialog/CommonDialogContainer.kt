package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle20B

@Composable
fun CommonDialogContainer(
    isShow: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    if (isShow) {
        Dialog(onDismissRequest = onDismissRequest) {
            Column(
                content = content,
                modifier = Modifier
                    .background(MyLightBlack, RoundedCornerShape(10.dp))
                    .border(1.dp, MyWhite, RoundedCornerShape(10.dp))
            )
        }
    }
}

@Composable
fun CommonConfirmDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    cancelable: Boolean = true,
    title: (@Composable () -> Unit)? = null,
    contents: @Composable () -> Unit,
    okText: String = "확인",
    cancelText: String = "취소",
    okTextStyle: TextStyle = textStyle20B(),
    cancelTextStyle: TextStyle = textStyle20B(),
    okButtonColor: Color = MainColor,
    isSingleButton: Boolean = false,
    okClick: () -> Unit,
    cancelClick: () -> Unit = onDismiss
) {
    CommonDialogContainer(
        isShow = isShow,
        onDismissRequest = { if (cancelable) onDismiss() }
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        title?.let {
            it.invoke()
            Spacer(modifier = Modifier.height(10.dp))
        }
        contents.invoke()
        Spacer(modifier = Modifier.height(25.dp))
        Row(modifier = Modifier.padding(horizontal = 20.dp)) {
            if (isSingleButton.not()) {
                CommonButton(
                    text = cancelText,
                    style = cancelTextStyle,
                    color = MyGray,
                    shape = RoundedCornerShape(3.dp),
                    modifier = Modifier
                        .weight(1f)
                        .nonRippleClickable(cancelClick)
                )
                Spacer(modifier = Modifier.width(20.dp))
            }
            CommonButton(
                text = okText,
                color = okButtonColor,
                style = okTextStyle,
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier
                    .weight(1f)
                    .nonRippleClickable(okClick)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}