package com.example.lolketingcompose.ui.custom

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.util.formatNumber
import com.example.lolketingcompose.util.textStyle14B
import kotlin.math.min

@Composable
fun TextProgress(
    modifier: Modifier = Modifier,
    maxValue: Int,
    value: Int
) {
    val percent = runCatching {
        min(value.toFloat() / maxValue, 1f)
    }.getOrElse { 0f }

    var isStart by remember { mutableStateOf(false) }
    val state by animateFloatAsState(
        targetValue = if (isStart) percent else 0f,
        animationSpec = tween(durationMillis = 2500),
        label = ""
    )
    LaunchedEffect(Unit) {
        isStart = true
    }

    Box(
        modifier = modifier
            .height(22.dp)
            .background(MyLightGray, RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(state)
                .height(22.dp)
                .background(MainColor, RoundedCornerShape(16.dp))
        )
        Text(
            text = "${formatNumber(value)} / ${formatNumber(maxValue)}",
            style = textStyle14B(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}