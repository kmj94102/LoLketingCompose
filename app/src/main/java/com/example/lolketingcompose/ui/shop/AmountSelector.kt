package com.example.lolketingcompose.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16B

@Preview
@Composable
fun AmountSelector(
    value: Int = 10,
    onMinusClick: () -> Unit = {},
    onPlusClick: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.border(1.dp, MyWhite, RoundedCornerShape(5.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_minus),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .nonRippleClickable(onMinusClick)
        )
        Box(
            modifier = Modifier
                .height(33.dp)
                .width(1.dp)
                .background(MyGray)
        )

        Text(
            text = "$value",
            style = textStyle16B(textAlign = TextAlign.Center),
            modifier = Modifier.width(34.dp)
        )
        Box(
            modifier = Modifier
                .height(33.dp)
                .width(1.dp)
                .background(MyGray)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .padding(5.dp)
                .nonRippleClickable(onPlusClick)
        )
    }
}