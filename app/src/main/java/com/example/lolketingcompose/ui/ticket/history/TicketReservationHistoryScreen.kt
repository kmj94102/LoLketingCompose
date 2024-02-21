package com.example.lolketingcompose.ui.ticket.history

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16
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

    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            CommonHeader(
                onBackClick = onBackClick,
                title = "티켓 정보"
            )
        },
        bodyContent = {
            TicketHistoryInfo(viewModel.ticketInfo.value)
        },
        bottomContent = {
            CommonButton(
                text = "환불하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable { }
            )
        }
    )
}


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun TicketHistoryInfo(info: TicketInfo) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.5711096, 126.9813897), 14.0)
    }

    LazyColumn(
        contentPadding = PaddingValues(vertical = 15.dp, horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text(text = info.getGameTitle(), style = textStyle22B(color = MainColor))
        }

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
                    .border(1.dp, MyWhite)
            ) {
                Marker(
                    state = MarkerState(LatLng(37.5711096, 126.9813897)),
                    captionText = "롤파크"
                )
            }
        }

        item {
            Text(
                text = "티켓 및 환불 안내 >",
                style = textStyle16(),
                modifier = Modifier.nonRippleClickable { })
        }
    }
}

@Composable
fun TicketHistoryItem(
    title: String,
    content: String
) {
    Row {
        Text(text = title, style = textStyle16(color = MyGray))
        Text(
            text = content,
            style = textStyle16B(textAlign = TextAlign.End),
            modifier = Modifier.weight(1f)
        )
    }
}