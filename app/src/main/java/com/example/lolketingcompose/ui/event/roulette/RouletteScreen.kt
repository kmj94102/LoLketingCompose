package com.example.lolketingcompose.ui.event.roulette

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.dialog.RouletteGuideDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.util.FillMaxWithImage
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle14
import com.example.lolketingcompose.util.textStyle20
import com.example.lolketingcompose.util.textStyle22B

@Composable
fun RouletteScreen(
    onBackClick: () -> Unit,
    viewModel: RouletteViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val isStart = remember { mutableStateOf(false) }
    var isShow by remember { mutableStateOf(false) }

    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            CommonHeader(
                title = "RP 룰렛",
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            RouletteBody(
                deg = viewModel.deg.value,
                count = viewModel.count.value,
                isStart = isStart,
                finishedListener = {
                    isStart.value = false
                    status.updateMessage(viewModel.resultMessage.value)
                }
            )
        },
        bottomContent = {
            CommonButton(
                text = "돌리기",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable {
                        if (isStart.value) return@nonRippleClickable
                        if (viewModel.count.value <= 0) {
                            isShow = true
                        } else {
                            isStart.value = true
                            viewModel.rouletteStart()
                        }
                    }
            )
        }
    )

    RouletteGuideDialog(
        isShow = isShow,
        onDismiss = { isShow = false }
    )
}

@Composable
fun RouletteBody(
    deg: Float,
    count: Int,
    isStart: State<Boolean>,
    finishedListener: () -> Unit
) {
    val rotate = remember { Animatable(0f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 46.dp, horizontal = 20.dp)
    ) {
        Text(text = "100% 당첨 룰렛", style = textStyle22B(color = MainColor).copy(fontSize = 26.sp))
        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "돌리고 RP를 모아보세요!", style = textStyle14())

        Box(
            modifier = Modifier.weight(1f)
        ) {
            FillMaxWithImage(
                imageRes = R.drawable.img_roulette,
                modifier = Modifier
                    .padding(top = 36.dp)
                    .rotate(rotate.value)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_position),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(40.dp, 58.dp)
            )
        }

        Text(
            text = if (count > 0) {
                "${count}번 더 돌릴 수 있어요!"
            } else {
                "룰렛 횟수를 모두 소진하였습니다."
            },
            style = textStyle20()
        )
    }

    LaunchedEffect(deg) {
        if (isStart.value) {
            rotate.animateTo(0f, tween(0))
            rotate.animateTo(
                targetValue = (360 * 5f) + deg,
                tween(durationMillis = 5000, easing = FastOutSlowInEasing)
            )
            finishedListener()
        }
    }
}