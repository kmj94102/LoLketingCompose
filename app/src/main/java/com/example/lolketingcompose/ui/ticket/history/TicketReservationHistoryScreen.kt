package com.example.lolketingcompose.ui.ticket.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.dialog.NoRefundDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle22B
import com.example.network.model.TicketInfo
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

@Composable
fun TicketReservationHistoryScreen(
    onBackClick: () -> Unit,
    viewModel: TicketReservationHistoryViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        onBackClick = onBackClick,
        headerContent = {
            CommonHeader(
                onBackClick = onBackClick,
                title = "티켓 정보",
                tailIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 20.dp)
                    )
                }
            )
        },
        bodyContent = {
            TicketHistoryInfo(
                info = viewModel.ticketInfo.value,
                refundTicket = viewModel::refundTicket
            )
        }
    )

    NoRefundDialog(
        isShow = viewModel.noRefund.value,
        onDismiss = { viewModel.dismiss() }
    )
}


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun TicketHistoryInfo(
    info: TicketInfo,
    refundTicket: () -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.5711096, 126.9813897), 14.0)
    }

    Column(modifier = Modifier.padding(bottom = 15.dp)) {
        Image(
            painter = painterResource(id = R.drawable.img_lck_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 15.dp)
                .align(Alignment.CenterHorizontally)
                .size(170.dp, 120.dp)
        )

        Text(
            text = info.getGameTitle(),
            style = textStyle22B(color = MainColor, textAlign = TextAlign.Center),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(15.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 10.dp,
                    bottom = 40.dp,
                    start = 15.dp,
                    end = 15.dp
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(bottom = 17.dp)
                    .background(MyLightBlack, RoundedCornerShape(15.dp))
                    .border(1.dp, MyWhite, RoundedCornerShape(15.dp))
            ) {
                val itemList = listOf(
                    Pair("시간", info.time),
                    Pair("좌석", info.getSeatsInfo()),
                    Pair("장소", "LoL PARK"),
                )
                items(itemList) { (title, content) ->
                    TicketHistoryItem(title = title, content = content)
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    NaverMap(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, MyWhite)
                    ) {
                        Marker(
                            state = MarkerState(LatLng(37.5711096, 126.9813897)),
                            captionText = "롤파크"
                        )
                    }
                }
            }
            Text(
                text = "티켓 환불",
                style = textStyle16B(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(MainColor, RoundedCornerShape(22.dp))
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .nonRippleClickable(refundTicket)
            )
        }
    }
}

@Composable
fun TicketHistoryItem(
    title: String,
    content: String
) {
    Column {
        Text(text = title, style = textStyle12(color = MyGray))
        Text(
            text = content,
            style = textStyle16B(textAlign = TextAlign.End).copy(fontSize = 18.sp)
        )
    }
}