package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.util.NumberCommaTransformation
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle20
import com.example.lolketingcompose.util.textStyle22B
import kotlin.math.min

@Composable
fun CashChargingDialog(
    isShow: Boolean,
    myCash: Int,
    onDismiss: () -> Unit,
    listener: (Int) -> Unit
) {
    var amount by remember { mutableIntStateOf(0) }
    val guideText = buildAnnotatedString {
        append("‘롤켓팅'의 캐시는 ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("가상 머니")
        }
        append("입니다.\n원하시는 충전 금액을 입력하시고\n충전하기 버튼을 눌러주세요.\n\n")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MainColor)) {
            append("최소 충전")
        }
        append(" 금액은 ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("1,000원")
        }
        append("이며\n")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MainColor)) {
            append("최대 보유")
        }
        append(" 캐시는 ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("1억원 ")
        }
        append("입니다.")
    }

    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        title = {
            Text(
                text = "캐시 충전",
                style = textStyle22B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        contents = {
            Column {
                Text(
                    text = guideText,
                    style = textStyle16(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 25.dp)
                ) {
                    Text(text = "My 캐시", style = textStyle16(color = MyLightGray))
                    Text(
                        text = myCash.formatWithComma().plus("원"),
                        style = textStyle16B(textAlign = TextAlign.End),
                        modifier = Modifier.weight(1f)
                    )
                }

                CommonTextField(
                    value = "$amount",
                    onTextChange = { value ->
                        runCatching {
                            if (value.replace(",", "").all { it.isDigit() }) {
                                amount = min(value.toInt(), 100_000_000)
                            }
                        }.onFailure { amount = 0 }
                    },
                    hint = "충전할 금액",
                    textStyle = textStyle20(textAlign = TextAlign.End),
                    contentPadding = PaddingValues(10.dp),
                    keyboardType = KeyboardType.Number,
                    trailingIcon = {
                        Text(text = "원", style = textStyle20())
                    },
                    visualTransformation = NumberCommaTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
            }
        },
        okText = "충전하기",
        okClick = {
            listener(amount)
            amount = 0
            onDismiss()
        }
    )
}