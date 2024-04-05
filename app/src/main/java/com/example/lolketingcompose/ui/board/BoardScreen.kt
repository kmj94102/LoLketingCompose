package com.example.lolketingcompose.ui.board

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16B


@Composable
fun BoardScreen(
    onBackClick: () -> Unit,
    goToWrite: () -> Unit,
    viewModel: BoardViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            BoardHeader(onBackClick = onBackClick)
        },
        bodyContent = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "작성된 게시글이 없습니다.",
                    style = textStyle16B(color = MyGray),
                    modifier = Modifier.nonRippleClickable(goToWrite)
                )
            }
        }
    )
}

@Composable
fun BoardHeader(
    onBackClick: () -> Unit
) {
    CommonHeader(
        onBackClick = onBackClick,
        centerItem = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "전체보기", style = textStyle16B(MainColor))
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_up),
                    contentDescription = null,
                    modifier = Modifier.rotate(180f)
                )
            }
        },
        tailIcon = {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.width(5.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        }
    )
}