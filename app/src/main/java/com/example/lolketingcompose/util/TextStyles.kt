package com.example.lolketingcompose.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.theme.MyWhite

val fontFamily = FontFamily(
    Font(R.font.noto_serif, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.noto_serif_semi_bold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.noto_serif_bold, FontWeight.Bold, FontStyle.Normal)
)

fun textStyle14B(color: Color = MyWhite): TextStyle = TextStyle(
    color = color,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    fontFamily = fontFamily
)