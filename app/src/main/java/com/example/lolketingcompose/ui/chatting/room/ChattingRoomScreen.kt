package com.example.lolketingcompose.ui.chatting.room

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.EmptyContainer
import com.example.lolketingcompose.ui.custom.EmptyStateLazyColumn
import com.example.lolketingcompose.ui.custom.RestrictedTextField
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.SubColor
import com.example.lolketingcompose.util.textStyle12B
import com.example.lolketingcompose.util.textStyle16B
import com.example.network.model.ChattingItem

@Composable
fun ChattingRoomScreen(
    onBackClick: () -> Unit,
    viewModel: ChattingRoomViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    TopBodyBottomContainer(
        status = status,
        topContent = {
            CommonHeader(
                centerItem = {
                    Text(
                        text = "${viewModel.roomInfo.leftTeam} vs ${viewModel.roomInfo.rightTeam}",
                        style = textStyle16B(color = MainColor, textAlign = TextAlign.Center),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 50.dp)
                    )
                },
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            EmptyStateLazyColumn(
                state = lazyListState,
                contentPadding = PaddingValues(
                    top = 20.dp,
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 30.dp
                ),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                list = viewModel.list,
                content = {
                    if (it.isLeftTeam) {
                        LeftChatItem(item = it)
                    } else {
                        RightChatItem(item = it)
                    }
                },
                emptyContent = {
                    EmptyContainer(text = "아직 작성된 채팅이 없습니다\n첫 채팅을 입력해 주세요")
                }
            )
        },
        bottomContent = {
            RestrictedTextField(
                maxLength = 600,
                value = viewModel.message.value,
                onTextChange = viewModel::updateChattingMessage,
                onRegister = viewModel::addChat,
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
            )
        }
    )

    LaunchedEffect(viewModel.list.size) {
        if (viewModel.list.isNotEmpty()) {
            lazyListState.scrollToItem(viewModel.list.size - 1)
        }
    }
}

@Composable
fun LeftChatItem(
    item: ChattingItem,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        val rounded = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomEnd = 10.dp)
        Text(text = item.nickname, style = textStyle12B(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = item.message,
            style = textStyle12B(),
            modifier = Modifier
                .border(1.dp, MainColor, rounded)
                .background(MyLightBlack, rounded)
                .padding(10.dp)
        )
    }
}

@Composable
fun RightChatItem(
    item: ChattingItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        val rounded = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 10.dp)
        Text(
            text = item.nickname,
            style = textStyle12B(textAlign = TextAlign.End),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = item.message,
            style = textStyle12B(textAlign = TextAlign.End),
            modifier = Modifier
                .border(1.dp, SubColor, rounded)
                .background(MyLightBlack, rounded)
                .padding(10.dp)
        )
    }
}