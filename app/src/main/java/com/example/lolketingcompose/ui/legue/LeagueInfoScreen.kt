package com.example.lolketingcompose.ui.legue

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.BaseStatus
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle20B
import kotlinx.coroutines.launch

@Composable
fun LeagueInfoScreen(
    onBackClick: () -> Unit
) {
    HeaderBodyContainer(
        status = BaseStatus(),
        headerContent = {
            LeagueInfoHeader(onBackClick)
        },
        bodyContent = {
            LeagueInfoBody()
        }
    )
}

@Composable
fun LeagueInfoHeader(onBackClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.img_leagueinfo),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.height(250.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, start = 20.dp)
                .size(24.dp)
                .nonRippleClickable(onBackClick)
        )
        Text(
            text = "리그 정보",
            style = textStyle16B(color = MainColor),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 18.dp)
        )
        Text(
            text = "LoL 챔피언스 코리아\n스프링 스플릿 대회 안내",
            style = textStyle16B(textAlign = TextAlign.Center),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LeagueInfoBody(modifier: Modifier = Modifier) {
    val tabList = mutableListOf("개요", "진행방식", "진행방식")
    val state = rememberPagerState { tabList.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = modifier) {
        TabRow(
            selectedTabIndex = state.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    color = MainColor,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[state.currentPage])
                )
            },
            containerColor = MyBlack,
            tabs = {
                tabList.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == state.currentPage,
                        onClick = {
                            coroutineScope.launch {
                                state.animateScrollToPage(index)
                            }
                        },
                        text = { Text(text = tab) },
                        selectedContentColor = MainColor,
                        unselectedContentColor = MyWhite
                    )
                }
            }
        )

        HorizontalPager(
            state = state,
            modifier = Modifier.weight(1f)
        ) {
            when (it) {
                1 -> {
                    HowToProceed()
                }

                2 -> {
                    CompetitionPrizeMoney()
                }

                else -> LeagueInfoSummaryContainer()
            }
        }
    }
}

@Composable
fun LeagueInfoSummaryContainer() {
    LazyColumn(
        contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = stringResource(id = R.string.what_is_lck),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.guide_what_is_lck),
                style = textStyle12(color = MyLightGray)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(id = R.string.league_schedule),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.guide_league_schedule),
                style = textStyle12(color = MyLightGray)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(id = R.string.total_prize_money),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.guide_total_prize_money),
                style = textStyle12(color = MyLightGray)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(id = R.string.relay),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.guide_relay),
                style = textStyle12(color = MyLightGray)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(id = R.string.place),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.guide_place),
                style = textStyle12(color = MyLightGray)
            )
        }
    }
}

@Composable
fun HowToProceed() {
    LazyColumn(
        contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Text(
                text = stringResource(id = R.string.regular_season),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, MainColor, RoundedCornerShape(10.dp))
                        .padding(vertical = 10.dp)
                ) {
                    Text(text = "1라운드", style = textStyle20B())
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "3월 6일 ~ 4월 12일", style = textStyle12(color = MyLightGray))
                }
                Spacer(modifier = Modifier.width(24.dp))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, MainColor, RoundedCornerShape(10.dp))
                        .padding(vertical = 10.dp)
                ) {
                    Text(text = "2라운드", style = textStyle20B())
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "2월 5일 ~ 3월 6일", style = textStyle12(color = MyLightGray))
                }
            }
            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(id = R.string.post_season),
                style = textStyle16B(color = MainColor)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.guide_post_season),
                style = textStyle12(color = MyLightGray)
            )
            Spacer(modifier = Modifier.height(15.dp))

            Image(
                painter = painterResource(id = R.drawable.img_season),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
fun CompetitionPrizeMoney() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.lck_prize_money_information),
            style = textStyle16B(color = MainColor)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Image(
            painter = painterResource(id = R.drawable.img_reward),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}