package com.example.lolketingcompose.ui.shop

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyWhite

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    imageList: List<String> = mockImageList(),
) {

    val state = rememberPagerState { imageList.size }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(310.dp)
            .background(MyWhite)
    ) {
        HorizontalPager(
            state = state,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = imageList[page],
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        if (imageList.size > 1) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(state.pageCount) { iteration ->
                    val color = if (state.currentPage == iteration) MainColor else MyLightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .border(1.dp, MyBlack, CircleShape)
                            .size(10.dp)
                    )
                }
            }
        }
    }
}

private fun mockImageList() =
    listOf(
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/goods%2F001-1.jpg?alt=media&token=b29e5d94-6a2e-49ff-a878-0debe595409f",
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/goods%2F001-2.jpg?alt=media&token=1dcc2451-8bc9-4504-9950-a7da77b5e58b",
        "https://firebasestorage.googleapis.com/v0/b/lolketing.appspot.com/o/goods%2F001-3.jpg?alt=media&token=00bc7667-46b6-47d1-9b52-1adf6ab56aa6"
    )