package com.example.lolketingcompose.ui.ticket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle14B
import com.example.network.model.Game

@Preview
@Composable
fun TicketItem(
    item: Game = Game.mockData(),
    onClick: (Int) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .nonRippleClickable { onClick(item.gameId) }
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp)
                }
                .background(MainColor, RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp))
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(20.dp)
                    .graphicsLayer {
                        translationX = -10.dp.toPx()
                    }
                    .background(MyBlack, CircleShape)

            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(16.dp)
                    .graphicsLayer {
                        translationY = -8.dp.toPx()
                        translationX = 8.dp.toPx()
                    }
                    .background(MyBlack, CircleShape)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(16.dp)
                    .graphicsLayer {
                        translationY = 8.dp.toPx()
                        translationX = 8.dp.toPx()
                    }
                    .background(MyBlack, CircleShape)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                Text(
                    text = item.leftTeam,
                    style = textStyle14B(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )
                Text(
                    text = "VS",
                    style = textStyle14B(color = MyBlack),
                )
                Text(
                    text = item.rightTeam,
                    style = textStyle14B(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .width(100.dp)
                .fillMaxHeight()
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp)
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, MainColor, RoundedCornerShape(topEnd = 5.dp, bottomEnd = 5.dp))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .size(16.dp)
                    .graphicsLayer {
                        translationY = -7.dp.toPx()
                        translationX = -8.dp.toPx()
                    }
                    .background(MyBlack, CircleShape)
                    .border(1.dp, MainColor, CircleShape)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .size(16.dp)
                    .graphicsLayer {
                        translationY = 7.dp.toPx()
                        translationX = -8.dp.toPx()
                    }
                    .background(MyBlack, CircleShape)
                    .border(1.dp, MainColor, CircleShape)
            )

            Text(
                text = item.gameDate.replace(" ", "\n"),
                style = textStyle14B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
    }
}