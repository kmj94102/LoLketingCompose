package com.example.lolketingcompose.ui.board.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.board.BoardBalloon
import com.example.lolketingcompose.ui.board.BoardItem
import com.example.lolketingcompose.ui.custom.RestrictedTextField
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle12B
import com.example.lolketingcompose.util.textStyle14
import com.example.network.model.Comment

@Composable
fun BoardDetailScreen(
    onBackClick: () -> Unit,
    viewModel: BoardDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            CommonHeader(onBackClick = onBackClick)
        },
        bodyContent = {
            BoardDetailBody(
                viewModel = viewModel
            )
        },
        bottomContent = {
            RestrictedTextField(
                maxLength = 200,
                value = viewModel.commentWrite.value,
                onTextChange = viewModel::updateComment,
                onRegister = viewModel::insertComment,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 25.dp)
            )
        }
    )
}

@Composable
fun BoardDetailBody(
    modifier: Modifier = Modifier,
    viewModel: BoardDetailViewModel
) {
    val item = viewModel.info.value

    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp),
        modifier = modifier.padding(bottom = 5.dp)
    ) {
        item {
            BoardItem(
                board = item.toBoard(),
                onLikeClick = viewModel::updateLike,
                onModifierClick = {},
                onDeleteClick = viewModel::deleteBoard,
                imageMaxHeight = Dp.Unspecified,
                onReportClick = {}
            )
        }

        items(item.commentList) {
            CommentItem(
                comment = it,
                onModifierClick = {},
                onDeleteClick = viewModel::deleteComment,
                onReportClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun CommentItem(
    modifier: Modifier = Modifier,
    comment: Comment,
    onModifierClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
    onReportClick: (Int) -> Unit,
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Text(text = comment.nickname, style = textStyle12B(color = MyGray))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = comment.timestamp, style = textStyle12(color = MyGray))
            Spacer(modifier = Modifier.weight(1f))
            BoardBalloon(
                isAuthor = comment.isAuthor,
                onModifierClick = {
                    onModifierClick(comment.commentId)
                },
                onDeleteClick = {
                    onDeleteClick(comment.commentId)
                },
                onReportClick = {
                    onReportClick(comment.commentId)
                },
            )
        }

        Text(
            text = comment.contents,
            style = textStyle14(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))

        Divider(color = MyGray)
    }
}