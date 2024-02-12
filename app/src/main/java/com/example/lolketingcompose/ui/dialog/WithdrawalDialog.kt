package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.theme.MyRed
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle22B
import com.example.lolketingcompose.util.toast

@Composable
fun WithdrawalDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    listener: () -> Unit
) {
    var textFieldValue by remember { mutableStateOf("") }
    val context = LocalContext.current

    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        title = {
            Text(
                text = stringResource(id = R.string.withdrawal),
                style = textStyle22B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        contents = {
            Column {
                Text(
                    text = stringResource(id = R.string.guide_withdrawal),
                    style = textStyle16(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(25.dp))
                CommonTextField(
                    value = textFieldValue,
                    onTextChange = { textFieldValue = it },
                    contentPadding = PaddingValues(10.dp),
                    hint = stringResource(id = R.string.withdrawal),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        },
        okClick = {
            if (textFieldValue == context.getString(R.string.withdrawal)) {
                listener()
                onDismiss()
            } else {
                context.toast(R.string.wrong_input_withdrawal)
            }
        },
        okText = stringResource(id = R.string.do_withdrawal),
        okButtonColor = MyRed
    )
}