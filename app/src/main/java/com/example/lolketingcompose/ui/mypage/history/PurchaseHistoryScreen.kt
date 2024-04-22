package com.example.lolketingcompose.ui.mypage.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.custom.EmptyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.ui.ticket.TicketItem
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle14
import com.example.lolketingcompose.util.textStyle14B
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.network.model.PurchaseHistoryInfo

@Composable
fun PurchaseHistoryScreen(
    onBackClick: () -> Unit,
    goToTicketReservationHistory: (String) -> Unit,
    viewModel: PurchaseHistoryViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonHeader(
                title = "구매 내역",
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            PurchaseHistorySelector(
                isTicket = viewModel.isTicket.value,
                onClick = viewModel::updateSelector,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 15.dp)
            )
            if (viewModel.isTicket.value) {
                TicketHistoryItems(
                    list = viewModel.ticketList,
                    goToTicketReservationHistory = goToTicketReservationHistory
                )
            } else {
                GoodsHistoryItems(list = viewModel.goodsList)
            }
        }
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchPurchaseHistory()
            viewModel.fetchGoodsHistory()
        }
    }
}

@Composable
fun PurchaseHistorySelector(
    isTicket: Boolean,
    onClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(3.dp),
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "티켓",
                style = textStyle16B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .weight(1f)
                    .background(if (isTicket) MainColor else MyGray)
                    .padding(vertical = 5.dp)
                    .nonRippleClickable { onClick(true) }
            )

            Text(
                text = "굿즈",
                style = textStyle16B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .weight(1f)
                    .background(if (isTicket) MyGray else MainColor)
                    .padding(vertical = 5.dp)
                    .nonRippleClickable { onClick(false) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TicketHistoryItems(
    modifier: Modifier = Modifier,
    list: List<PurchaseHistoryInfo>,
    goToTicketReservationHistory: (String) -> Unit
) {
    if (list.isEmpty()) {
        EmptyContainer(text = "구매 내역이 없습니다")
    }

    LazyColumn(
        contentPadding = PaddingValues(top = 5.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        list.forEach {
            if (it is PurchaseHistoryInfo.PurchaseHistoryDate) {
                stickyHeader { HistoryDateContainer(date = it.date) }
            } else if (it is PurchaseHistoryInfo.PurchaseTicketHistory) {
                item {
                    TicketItem(
                        leftTeam = it.leftTeam,
                        rightTeam = it.rightTeam,
                        info = it.seatNumbers,
                        onClick = {
                            goToTicketReservationHistory(it.reservationIds)
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GoodsHistoryItems(
    modifier: Modifier = Modifier,
    list: List<PurchaseHistoryInfo>
) {
    if (list.isEmpty()) {
        EmptyContainer(text = "구매 내역이 없습니다")
    }

    LazyColumn(
        contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        list.forEach {
            if (it is PurchaseHistoryInfo.PurchaseHistoryDate) {
                stickyHeader {
                    HistoryDateContainer(
                        date = it.date,
                        modifier = Modifier
                            .background(MyBlack)
                            .padding(start = 20.dp)
                    )
                }
            } else if (it is PurchaseHistoryInfo.PurchaseGoodsHistory) {
                item {
                    GoodsHistoryItem(
                        item = it,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun GoodsHistoryItem(
    item: PurchaseHistoryInfo.PurchaseGoodsHistory
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MyWhite, RoundedCornerShape(10.dp))
                .border(1.dp, MainColor, RoundedCornerShape(10.dp))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(text = "[${item.category}]", style = textStyle12(color = MyGray))
            Spacer(modifier = Modifier.height(4.dp))

            Text(text = item.name, style = textStyle16B(), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(text = "수량", style = textStyle14(color = MyGray))
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "${item.amount}개", style = textStyle14B())
            }
            Spacer(modifier = Modifier.height(4.dp))

            Row {
                Text(text = "가격", style = textStyle14(color = MyGray))
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = item.price.formatWithComma().plus("원"),
                    style = textStyle14B(color = MainColor)
                )
            }
        }
    }
    Divider(color = MyGray)
}

@Composable
fun HistoryDateContainer(
    modifier: Modifier = Modifier,
    date: String
) {
    Text(
        text = date,
        style = textStyle16(),
        modifier = modifier
            .fillMaxWidth()
            .background(MyBlack)
            .padding(vertical = 10.dp)
    )
}