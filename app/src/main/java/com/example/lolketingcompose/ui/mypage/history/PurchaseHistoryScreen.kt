package com.example.lolketingcompose.ui.mypage.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.ticket.TicketItem
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.network.model.PurchaseHistoryInfo

@Composable
fun PurchaseHistoryScreen(
    onBackClick: () -> Unit,
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
                TicketHistoryItems(list = viewModel.list)
            } else {
                GoodsHistoryItems()
            }
        }
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchPurchaseHistory()
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
    list: List<PurchaseHistoryInfo>
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 5.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        list.forEach {
            if (it is PurchaseHistoryInfo.PurchaseHistoryDate) {
                stickyHeader { HistoryDateContainer(it.date) }
            } else if (it is PurchaseHistoryInfo.PurchaseTicketHistory) {
                item {
                    TicketItem(item = it.toGame())
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun GoodsHistoryItems(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp, start = 20.dp, end = 20.dp),
        modifier = modifier.fillMaxWidth()
    ) {

    }
}

@Composable
fun HistoryDateContainer(date: String) {
    Text(
        text = date,
        style = textStyle16(),
        modifier = Modifier
            .fillMaxWidth()
            .background(MyBlack)
            .padding(vertical = 10.dp)
    )
}