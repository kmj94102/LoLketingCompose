package com.example.lolketingcompose.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.textStyle20B

@Composable
fun CommonButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MainColor,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp)
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = color, shape = shape)
    ) {
        Text(
            text = text,
            color = MyWhite,
            style = textStyle20B(),
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}