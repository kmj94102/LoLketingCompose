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
import com.example.network.model.Grade
import kotlin.math.min

@Composable
fun GradeProgress(
    modifier: Modifier = Modifier,
    grade: String,
    value: Int,
    gradeUpdateListener: (String) -> Unit
) {
    val maxValue = Grade.getMaxPoint(grade)
    var isGradeUpdate by remember { mutableStateOf(false) }
    val percent = runCatching {
        min(value.toFloat() / maxValue, 1f)
    }.getOrElse { 0f }

    val state by animateFloatAsState(
        targetValue = percent,
        animationSpec = if (isGradeUpdate) tween(durationMillis = 0) else tween(durationMillis = 1500),
        finishedListener = {
            isGradeUpdate = it >= 1.0
            if (it >= 1.0) {
                val nextGrade = Grade.getNextGrade(grade, value)
                if (nextGrade.isNotEmpty()) {
                    gradeUpdateListener(nextGrade)
                }
            }
        },
        label = ""
    )
    val text =
        if (maxValue <= value) "MAX" else "${formatNumber(value)} / ${formatNumber(maxValue)}"

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
            text = text,
            style = textStyle14B(),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}