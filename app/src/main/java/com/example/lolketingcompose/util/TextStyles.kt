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
    Font(R.font.maru_buri_light, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.maru_buri_bold, FontWeight.Bold, FontStyle.Normal)
)

fun textStyle14B(color: Color = MyWhite): TextStyle = TextStyle(
    color = color,
    fontWeight = FontWeight.Bold,
    fontSize = 14.sp,
    fontFamily = fontFamily
)

fun textStyle16(color: Color = MyWhite): TextStyle = TextStyle(
    color = color,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    fontFamily = fontFamily
)

fun textStyle20(color: Color = MyWhite): TextStyle = TextStyle(
    color = color,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    fontFamily = fontFamily
)

fun textStyle20B(color: Color = MyWhite): TextStyle = TextStyle(
    color = color,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp,
    fontFamily = fontFamily,
)