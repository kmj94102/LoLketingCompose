package com.example.lolketingcompose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12B
import com.example.lolketingcompose.util.textStyle16B

@Composable
fun RestrictedTextField(
    maxLength: Int,
    value: String,
    modifier: Modifier = Modifier,
    minHeight: Dp = 85.dp,
    onTextChange: (String) -> Unit,
    onRegister: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .background(MyLightBlack, RoundedCornerShape(3.dp))
            .border(1.dp, MyWhite, RoundedCornerShape(3.dp))
            .nonRippleClickable { focusRequester.requestFocus() }
    ) {
        CommonTextField(
            value = value,
            onTextChange = {
                if (it.length <= maxLength) {
                    onTextChange(it)
                }
            },
            textStyle = textStyle12B(),
            singleLine = false,
            maxLines = Int.MAX_VALUE,
            focusedColor = Color.Transparent,
            unFocusedColor = Color.Transparent,
            imeAction = ImeAction.None,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 40.dp)
                .focusRequester(focusRequester)
        )
        Text(
            text = "${value.length}/$maxLength",
            style = textStyle12B(),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 5.dp, start = 10.dp)
        )
        Text(
            text = "등록",
            style = textStyle16B(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 5.dp, end = 10.dp)
                .background(MainColor, RoundedCornerShape(3.dp))
                .padding(vertical = 2.dp, horizontal = 3.dp)
                .nonRippleClickable {
                    focusManager.clearFocus()
                    onRegister()
                }
        )
    }
}