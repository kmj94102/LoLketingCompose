package com.example.lolketingcompose.ui.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.navigation.NavScreen
import com.example.lolketingcompose.structure.BaseContainer
import com.example.lolketingcompose.structure.BaseStatus
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle14B

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    goToScreen: (String) -> Unit,
    goToLogin: () -> Unit
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    BaseContainer(
        status = status,
    ) {
        HomeContent(status, goToScreen)
    }

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            if (viewModel.isLogin().not()) {
                goToLogin()
            }
        }
    }
}

@Composable
fun HomeContent(
    status: BaseStatus,
    goToScreen: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.img_banner1),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 250.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(35.dp)) {
                HomeIcon(
                    item = HomeIconItem(
                        iconRes = R.drawable.ic_board,
                        text = "게시판",
                        onClick = { status.temporaryInformationMessage() }
                    )
                )

                HomeIcon(
                    HomeIconItem(
                        iconRes = R.drawable.ic_event,
                        text = "이벤트",
                        onClick = { goToScreen(NavScreen.LolketingEvent.item.routeWithPostFix) }
                    )
                )

                HomeIcon(
                    item = HomeIconItem(
                        iconRes = R.drawable.ic_profile,
                        text = "내 정보",
                        onClick = { goToScreen(NavScreen.MyPage.item.routeWithPostFix) }
                    )
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(35.dp)) {
                HomeIcon(
                    item = HomeIconItem(
                        iconRes = R.drawable.ic_trophy,
                        text = "리그 정보",
                        onClick = { goToScreen(NavScreen.LeagueInfo.item.routeWithPostFix) }
                    )
                )

                HomeIcon(
                    HomeIconItem(
                        iconRes = R.drawable.ic_ticket,
                        text = "티켓 예메",
                        onClick = { goToScreen(NavScreen.TicketList.item.routeWithPostFix) }
                    )
                )

                HomeIcon(
                    item = HomeIconItem(
                        iconRes = R.drawable.ic_shopping,
                        text = "샵",
                        onClick = { goToScreen(NavScreen.Shop.item.routeWithPostFix) }
                    )
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(35.dp)) {
                HomeIcon(
                    item = HomeIconItem(
                        iconRes = R.drawable.ic_lol_guide,
                        text = "롤알못",
                        onClick = { goToScreen(NavScreen.LoLGuide.item.routeWithPostFix) }
                    )
                )

                HomeIcon(
                    HomeIconItem(
                        iconRes = R.drawable.ic_news,
                        text = "뉴스",
                        onClick = { goToScreen(NavScreen.News.item.routeWithPostFix) }
                    )
                )

                HomeIcon(
                    item = HomeIconItem(
                        iconRes = R.drawable.ic_chatting,
                        text = "채팅",
                        onClick = { status.temporaryInformationMessage() }
                    )
                )
            }
        }
    }
}

private fun BaseStatus.temporaryInformationMessage() {
    updateMessage("페이지 준비중입니다.")
}

@Composable
fun HomeIcon(
    item: HomeIconItem
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.nonRippleClickable(item.onClick)
    ) {
        Image(
            painter = painterResource(id = item.iconRes),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(bottom = 1.dp)
        )
        Text(text = item.text, style = textStyle14B())
    }
}

data class HomeIconItem(
    @DrawableRes
    val iconRes: Int,
    val text: String,
    val onClick: () -> Unit
)