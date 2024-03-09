package com.example.lolketingcompose.ui.ticket

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle14
import com.example.lolketingcompose.util.textStyle14B
import com.example.network.model.Game

@Composable
fun TicketListScreen(
    onBackClick: () -> Unit,
    goToReservation: (Int) -> Unit,
    viewModel: TicketListViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    HeaderBodyContainer(
        status = status,
        headerContent = {
            TicketListHeader(onBackClick)
        },
        bodyContent = {
            TicketListBody(viewModel.list, goToReservation)
        }
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchGameList()
        }
    }
}

@Composable
fun TicketListHeader(
    onBackClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color(0xFFD690A5))
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, start = 20.dp)
                .size(24.dp)
                .nonRippleClickable(onBackClick)
        )
        Image(
            painter = painterResource(id = R.drawable.img_ticket),
            contentDescription = null,
            modifier = Modifier
                .size(290.dp, 250.dp)
                .align(Alignment.TopEnd)
        )
        Text(
            text = "티켓 예매",
            style = textStyle14B().copy(fontSize = 40.sp),
            modifier = Modifier.padding(top = 56.dp, start = 20.dp)
        )
    }
}

@Composable
fun TicketListBody(
    list: List<Game>,
    goToReservation: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .size(15.dp)
                .background(MainColor)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "예매 가능", style = textStyle14())
        Spacer(modifier = Modifier.width(20.dp))

        Box(
            modifier = Modifier
                .size(15.dp)
                .background(MyYellow)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "경기 종료", style = textStyle14())
        Spacer(modifier = Modifier.width(20.dp))

        Box(
            modifier = Modifier
                .size(15.dp)
                .background(MyGray)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "매진", style = textStyle14())
    }


    LazyColumn(
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        items(list) {
            TicketItem(
                leftTeam = it.leftTeam,
                rightTeam = it.rightTeam,
                info = it.gameDate.replace(" ", "\n"),
                onClick = { goToReservation(it.gameId) }
            )
        }
    }
}