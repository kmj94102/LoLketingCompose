package com.example.lolketingcompose.ui.ticket.reservation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.dialog.CashChargingDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle22B
import com.example.network.model.ReservationInfo

@Composable
fun TicketReservationScreen(
    onBackClick: () -> Unit,
    goToTicketInfo: (String) -> Unit,
    goToGuide: () -> Unit,
    viewModel: TicketReservationViewModel = hiltViewModel()
) {
    var isShow by remember { mutableStateOf(false) }
    val status by viewModel.status.collectAsStateWithLifecycle()
    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            CommonHeader(
                title = "티켓 예매",
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            TicketReservationBody(viewModel = viewModel, goToGuide = goToGuide)
        },
        bottomContent = {
            CommonButton(
                text = "예매하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable(viewModel::makeReservation)
            )
        }
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchReservedSeats()
        }
    }

    val reservation = viewModel.reservation.value
    LaunchedEffect(reservation) {
        when (reservation) {
            is TicketReservationViewModel.Reservation.Init -> {}
            is TicketReservationViewModel.Reservation.CashCharging -> {
                isShow = true
                viewModel.updateInit()
            }

            is TicketReservationViewModel.Reservation.Success -> {
                goToTicketInfo(reservation.ids)
            }
        }
    }

    CashChargingDialog(
        isShow = isShow,
        myCash = viewModel.ticketInfo.value.cash,
        onDismiss = { isShow = false },
        listener = viewModel::cashCharging
    )
}

@Composable
fun ColumnScope.TicketReservationBody(
    viewModel: TicketReservationViewModel,
    goToGuide: () -> Unit
) {
    Spacer(modifier = Modifier.weight(1f))
    Text(
        text = "A홀",
        style = textStyle22B(color = MainColor),
        modifier = Modifier.align(Alignment.CenterHorizontally)
    )
    Spacer(modifier = Modifier.height(28.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(8),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(viewModel.list) { item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(1.dp, MyWhite, RoundedCornerShape(4.dp))
                    .background(
                        shape = RoundedCornerShape(4.dp),
                        color = when {
                            item.isReserved -> MyGray
                            item.isSelected -> MainColor
                            else -> MyBlack
                        }
                    )
                    .nonRippleClickable { viewModel.onSeatClick(item.number) }
            ) {
                Text(item.number, style = textStyle16B(), modifier = Modifier.padding(5.dp))
            }
        }
    }

    Spacer(modifier = Modifier.weight(1f))
    TicketReservationInfo(
        ticketPrice = viewModel.ticketPrice,
        numberOfPeople = viewModel.numberOfPeople.value,
        selectedSeatInfo = viewModel.selectedSeatInfo,
        ticketInfo = viewModel.ticketInfo.value,
        onNumberOfPeopleClick = viewModel::updateNumberOfPeople,
        goToGuide = goToGuide
    )
}

@Composable
fun TicketReservationInfo(
    ticketPrice: Int,
    numberOfPeople: Int,
    selectedSeatInfo: String,
    ticketInfo: ReservationInfo,
    onNumberOfPeopleClick: (Int) -> Unit,
    goToGuide: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MyLightBlack)
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = ticketInfo.date,
                style = textStyle16(),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "티켓 안내 >",
                style = textStyle16(color = MyLightGray),
                modifier = Modifier.nonRippleClickable(goToGuide)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Text(text = ticketInfo.gameTitle, style = textStyle22B(color = MainColor))
        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "인원", style = textStyle16B())
                Spacer(modifier = Modifier.width(10.dp))
                NumberOfPeopleButton(
                    number = 1,
                    isSelected = numberOfPeople == 1,
                    onClick = onNumberOfPeopleClick
                )
                Spacer(modifier = Modifier.width(10.dp))
                NumberOfPeopleButton(
                    number = 2,
                    isSelected = numberOfPeople == 2,
                    onClick = onNumberOfPeopleClick
                )
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "금액", style = textStyle16B())
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${(ticketPrice * numberOfPeople).formatWithComma()}원",
                    style = textStyle16B(color = MainColor)
                )
            }
        }
        Spacer(modifier = Modifier.height(14.dp))

        Row {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "좌석", style = textStyle16B())
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = selectedSeatInfo, style = textStyle16B(color = MainColor))
            }
            Row(modifier = Modifier.weight(1f)) {
                Text(text = "My 캐시", style = textStyle16B())
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${ticketInfo.cash.formatWithComma()}원",
                    style = textStyle16B(color = MainColor)
                )
            }
        }
    }
}

@Composable
fun NumberOfPeopleButton(
    number: Int,
    isSelected: Boolean,
    onClick: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, if (isSelected) MainColor else MyWhite),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MainColor else Color.Transparent
        ),
        modifier = Modifier.nonRippleClickable { onClick(number) }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(24.dp)
        ) {
            Text(text = "$number", style = textStyle16B())
        }
    }
}