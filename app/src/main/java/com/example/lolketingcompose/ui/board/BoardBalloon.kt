package com.example.lolketingcompose.ui.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16B
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder

@Composable
fun AuthorMenu(
    onModifierClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var maxWidth by remember { mutableIntStateOf(0) }

    Column {
        Text(
            text = "수정하기",
            style = textStyle16B(),
            modifier = Modifier
                .nonRippleClickable(onModifierClick)
                .padding(
                    horizontal = 20.dp,
                    vertical = 8.dp
                )
                .onGloballyPositioned {
                    maxWidth = maxWidth.coerceAtLeast(it.size.width)
                }
        )
        Divider(color = MyWhite, modifier = Modifier.width(maxWidth.dp))
        Text(
            text = "삭제하기",
            style = textStyle16B(),
            modifier = Modifier
                .nonRippleClickable(onDeleteClick)
                .padding(
                    horizontal = 20.dp,
                    vertical = 8.dp
                )
                .onGloballyPositioned {
                    maxWidth = maxWidth.coerceAtLeast(it.size.width)
                }
        )
    }
}

@Composable
fun VisitorMenu(
    onReportClick: () -> Unit
) {
    Column {
        Text(
            text = "신고하기",
            style = textStyle16B(),
            modifier = Modifier
                .nonRippleClickable(onReportClick)
                .padding(
                    horizontal = 20.dp,
                    vertical = 8.dp
                )
        )
    }
}

@Composable
fun BoardBalloon(
    isAuthor: Boolean,
    onModifierClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onReportClick: () -> Unit = {}
) {
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(0)
        setMarginHorizontal(12)
        setCornerRadius(8f)
        setBackgroundColorResource(R.color.balloon_color)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
    }
    var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }

    Balloon(
        builder = builder,
        onBalloonWindowInitialized = { balloonWindow = it },
        balloonContent = {
            if (isAuthor) {
                AuthorMenu(
                    onModifierClick = {
                        onModifierClick()
                        balloonWindow?.dismiss()
                    },
                    onDeleteClick = {
                        onDeleteClick()
                        balloonWindow?.dismiss()
                    }
                )
            } else {
                VisitorMenu(
                    onReportClick = {
                        onReportClick()
                        balloonWindow?.dismiss()
                    }
                )
            }
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_more),
            contentDescription = null,
            modifier = Modifier.nonRippleClickable {
                balloonWindow?.showAlignBottom()
            }
        )
    }
}