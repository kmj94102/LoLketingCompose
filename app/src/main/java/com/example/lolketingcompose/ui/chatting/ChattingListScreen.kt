package com.example.lolketingcompose.ui.chatting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.dialog.CustomDatePickerDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle12B
import com.example.lolketingcompose.util.textStyle16B
import com.example.network.model.ChattingRoomInfo
import com.example.network.model.Team

@Composable
fun ChattingListScreen(
    onBackClick: () -> Unit,
    goToChattingRoom: (ChattingRoomInfo, String) -> Unit,
    viewModel: ChattingListViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }

    TopBodyBottomContainer(
        status = status,
        topContent = {
            ChattingHeader(onBackClick)
        },
        bodyContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .nonRippleClickable { isShow = true }
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = viewModel.selectedDate.value, style = textStyle16B())
                Image(
                    painter = painterResource(id = R.drawable.ic_up),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .rotate(180f)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 15.dp,
                    bottom = 30.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                viewModel.list
                    .find { it.gameTime == "17:00" }
                    ?.let {
                        item {
                            ChattingListItem(
                                title = "1경기 (17:00)",
                                item = it,
                                onClick = { selectedTeam ->
                                    goToChattingRoom(it, selectedTeam)
                                }
                            )
                        }
                    }

                viewModel.list
                    .find { it.gameTime == "20:00" }
                    ?.let {
                        item {
                            ChattingListItem(
                                title = "2경기 (20:00)",
                                item = it,
                                onClick = { selectedTeam ->
                                    goToChattingRoom(it, selectedTeam)
                                }
                            )
                        }
                    }
            }
        },
        bottomContent = {
            Text(
                text = "응원하고 싶은 팀의 아이콘을 눌러 채팅방에 입장해주세요",
                style = textStyle12(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
    )

    CustomDatePickerDialog(
        isShow = isShow,
        selectedDate = viewModel.selectedDate.value,
        onDismiss = { isShow = false },
        okClick = viewModel::updateSelectedDate
    )
}

@Composable
fun ChattingHeader(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF00CBBF))
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_chatting),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .align(Alignment.TopEnd)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, start = 20.dp)
                .nonRippleClickable(onBackClick)
        )

        Text(
            text = "채팅",
            style = textStyle16B().copy(fontSize = 48.sp),
            modifier = Modifier.padding(top = 56.dp, start = 20.dp)
        )
    }
}

@Composable
fun ChattingListItem(
    title: String,
    item: ChattingRoomInfo,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .border(1.dp, MainColor, RoundedCornerShape(10.dp))
    ) {
        Text(
            text = title,
            style = textStyle12B(textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .background(MainColor, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .padding(vertical = 6.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 15.dp)
        ) {
            AsyncImage(
                model = Team.getTeamImage(item.leftTeam),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f)
                    .nonRippleClickable { onClick(item.leftTeam) }
            )

            Text(
                text = "VS",
                style = textStyle16B(
                    color = MyYellow,
                    textAlign = TextAlign.Center
                ).copy(fontSize = 24.sp)
            )

            AsyncImage(
                model = Team.getTeamImage(item.rightTeam),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .weight(1f)
                    .nonRippleClickable { onClick(item.rightTeam) }
            )
        }
    }
}