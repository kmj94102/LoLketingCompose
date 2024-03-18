package com.example.lolketingcompose.ui.event

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.CommonLottieAnimation
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.dialog.NewSignUpCouponDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.ui.theme.SubColor
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle14B
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle22B

@Composable
fun EventScreen(
    onBackClick: () -> Unit,
    goToMyPage: () -> Unit,
    goToRoulette: () -> Unit,
    viewModel: EventViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonHeader(
                title = "이벤트",
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 15.dp,
                    bottom = 30.dp,
                    start = 20.dp,
                    end = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    NewSignUpEventContainer(viewModel::insertNewUserCoupon)
                }
                item {
                    TicketReservationEventContainer(goToRoulette)
                }
            }
        }
    )

    NewSignUpCouponDialog(
        isShow = isShow,
        isIssued = viewModel.isIssued.value,
        onDismiss = { isShow = false },
        listener = goToMyPage
    )

    LaunchedEffect(viewModel.isComplete.value) {
        if (viewModel.isComplete.value) {
            isShow = true
            viewModel.updateIsComplete()
        }
    }

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchNewUserCoupon()
        }
    }
}

@Composable
fun EventContainer(
    eventCount: Int,
    title: String,
    content: AnnotatedString,
    jsonRawRes: Int,
    buttonText: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MyWhite, RoundedCornerShape(15.dp))
            .background(MyLightBlack, RoundedCornerShape(15.dp))
            .padding(vertical = 15.dp, horizontal = 20.dp)
    ) {
        Text(
            text = "Event $eventCount",
            style = textStyle14B(),
            modifier = Modifier
                .background(MainColor, RoundedCornerShape(15.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        )

        Text(text = title, style = textStyle22B(), modifier = Modifier.padding(top = 15.dp))

        Text(
            text = content,
            style = textStyle16(color = MyLightGray, textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        )

        CommonLottieAnimation(
            resId = jsonRawRes,
            modifier = Modifier
                .padding(top = 15.dp)
                .size(130.dp)
        )

        Text(
            text = buttonText,
            style = textStyle14B(),
            modifier = Modifier
                .padding(top = 15.dp)
                .background(SubColor, RoundedCornerShape(15.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .nonRippleClickable(onClick)
        )
    }
}

@Composable
fun NewSignUpEventContainer(
    onClick: () -> Unit
) {
    EventContainer(
        eventCount = 1,
        title = "신규 가입 웰컴 선물",
        content = buildAnnotatedString {
            append("신규 가입 기념\n500RP 쿠폰 발급")
        },
        jsonRawRes = R.raw.hello,
        buttonText = "쿠폰 받기",
        onClick = onClick
    )
}

@Composable
fun TicketReservationEventContainer(
    onClick: () -> Unit
) {
    EventContainer(
        eventCount = 2,
        title = "티켓 예매 이벤트",
        content = buildAnnotatedString {
            append("티켓 1장 당 룰렛 1회 이용 가능\n티켓 구매하시고 추가 ")
            withStyle(
                SpanStyle(fontWeight = FontWeight.Bold, color = MainColor)
            ) {
                append("RP")
            }
            append("를 받아가세요.\n")
            withStyle(
                SpanStyle(fontSize = 12.sp)
            ) {
                append("해당 이벤트는 횟 수 제한 없이 참여 가능합니다.")
            }
        },
        jsonRawRes = R.raw.ticket,
        buttonText = "룰렛 페이지 이동",
        onClick = onClick
    )
}