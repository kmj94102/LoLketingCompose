package com.example.lolketingcompose.ui.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.custom.EmptyContainer
import com.example.lolketingcompose.ui.custom.EmptyStateLazyColumn
import com.example.lolketingcompose.ui.dialog.TeamSelectDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle14B
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.network.model.Board

@Composable
fun BoardScreen(
    onBackClick: () -> Unit,
    goToWrite: () -> Unit,
    goToDetail: (Int) -> Unit,
    viewModel: BoardViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }

    HeaderBodyContainer(
        status = status,
        headerContent = {
            BoardHeader(
                onBackClick = onBackClick,
                goToWrite = goToWrite,
                onTeamSelectClick = { isShow = true },
                team = viewModel.selectTeam.value.teamName
            )
        },
        bodyContent = {
            BoardBody(
                viewModel = viewModel,
                goToDetail = goToDetail,
                goToWrite = goToWrite
            )
        }
    )

    TeamSelectDialog(
        isShow = isShow,
        onDismiss = { isShow = false },
        isAllVisible = true,
        onItemClick = viewModel::updateSelectTeam
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchBoardList(true)
        }
    }
}

@Composable
fun BoardHeader(
    onBackClick: () -> Unit,
    onTeamSelectClick: () -> Unit,
    goToWrite: () -> Unit,
    team: String
) {
    CommonHeader(
        onBackClick = onBackClick,
        centerItem = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.nonRippleClickable(onTeamSelectClick)
            ) {
                Text(text = team, style = textStyle16B(MainColor))
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_up),
                    contentDescription = null,
                    modifier = Modifier.rotate(180f)
                )
            }
        },
        tailIcon = {
            Row(modifier = Modifier.padding(end = 20.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier.nonRippleClickable(goToWrite)
                )
            }
        }
    )
}

@Composable
fun BoardBody(
    viewModel: BoardViewModel,
    goToDetail: (Int) -> Unit,
    goToWrite: () -> Unit
) {
    val list = viewModel.list

    EmptyStateLazyColumn(
        list = list,
        contentPadding = PaddingValues(bottom = 30.dp),
        content = {
            BoardItem(
                board = it,
                onLikeClick = viewModel::updateBoardLike,
                onItemClick = goToDetail,
                onModifierClick = {},
                onDeleteClick = viewModel::deleteBoard,
                onReportClick = {}
            )
        },
        emptyContent = {
            EmptyContainer(
                text = "작성된 게시글이 없습니다.",
                onClick = goToWrite
            )
        }
    )
}

@Composable
fun BoardItem(
    modifier: Modifier = Modifier,
    board: Board,
    imageMaxHeight: Dp = 200.dp,
    onItemClick: (Int) -> Unit = {},
    onLikeClick: (Int) -> Unit,
    onModifierClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onReportClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .nonRippleClickable { onItemClick(board.id) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "[${board.name}]", style = textStyle12(color = MyYellow))
            Spacer(modifier = Modifier.weight(1f))

            Text(text = board.timestamp, style = textStyle12(color = MyLightGray))
            Spacer(modifier = Modifier.width(5.dp))

            BoardBalloon(
                isAuthor = board.isAuthor,
                onModifierClick = {
                    onModifierClick(board.id)
                },
                onDeleteClick = {
                    onDeleteClick(board.id)
                },
                onReportClick = {
                    onReportClick(board.id)
                }
            )
        }

        Text(
            text = board.nickname,
            style = textStyle12(),
            modifier = Modifier.padding(start = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = board.contents,
            style = textStyle16(),
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        if (board.image.isNotEmpty()) {
            AsyncImage(
                model = board.image,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = imageMaxHeight)
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(5.dp))
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Image(
                painter = painterResource(
                    id = if (board.isLike) {
                        R.drawable.ic_heart_fill
                    } else {
                        R.drawable.ic_heart
                    }
                ),
                contentDescription = null,
                modifier = Modifier.nonRippleClickable { onLikeClick(board.id) }
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = board.likeCount.toString(),
                style = textStyle14B(),
                modifier = Modifier.nonRippleClickable { onLikeClick(board.id) }
            )
            Spacer(modifier = Modifier.width(10.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_comment),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = board.commentCount.toString(),
                style = textStyle14B(),
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Divider(color = MyGray)
    }
}