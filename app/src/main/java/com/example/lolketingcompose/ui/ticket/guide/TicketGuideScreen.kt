package com.example.lolketingcompose.ui.ticket.guide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.BaseStatus
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16B

@Composable
fun TicketGuideScreen(
    onBackClick: () -> Unit
) {
    HeaderBodyContainer(
        status = BaseStatus(),
        headerContent = {
            CommonHeader(
                onBackClick = onBackClick,
                title = "티켓 안내"
            )
        },
        bodyContent = {
            LazyColumn {
                item { ReservationInfoContainer() }
                item { RefundInfoContainer() }
                item {
                    CommonTicketGuidItem(
                        title = stringResource(id = R.string.pick_up_ticket),
                        content = stringResource(id = R.string.guide_pick_up_ticket)
                    )
                }
                item {
                    CommonTicketGuidItem(
                        title = stringResource(id = R.string.notice),
                        content = stringResource(id = R.string.guide_notice)
                    )
                }
                item {
                    CommonTicketGuidItem(
                        title = stringResource(id = R.string.restrictions),
                        content = stringResource(id = R.string.guide_restrictions)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TicketGuideItem(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    var isOpen by remember { mutableStateOf(false) }
    val rotateAnimation by animateFloatAsState(
        targetValue = if (isOpen) 0f else 180f,
        label = "isOpen state"
    )
    val transition = updateTransition(isOpen, label = "isOpen state")

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .nonRippleClickable { isOpen = isOpen.not() }
        ) {
            Text(
                text = title,
                style = textStyle16B(),
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.ic_up),
                contentDescription = null,
                modifier = Modifier.rotate(rotateAnimation)
            )
        }
        Divider(color = MyGray)
        transition.AnimatedVisibility(
            visible = { targetSelected -> targetSelected },
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MyLightBlack)
                    .padding(horizontal = 20.dp, vertical = 10.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
fun CommonTicketGuidItem(
    title: String,
    content: String
) {
    TicketGuideItem(
        title = title,
        content = {
            Text(text = content, style = textStyle12(), modifier = Modifier.fillMaxWidth())
        }
    )
}

@Composable
fun ReservationInfoContainer() {
    TicketGuideItem(
        title = stringResource(id = R.string.reserve_info),
        content = {
            Text(
                text = stringResource(id = R.string.reserve_open_label),
                style = textStyle12(color = MyGray)
            )
            Text(
                text = stringResource(id = R.string.reserve_open),
                style = textStyle16B()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.price),
                style = textStyle12(color = MyGray)
            )
            Text(
                text = stringResource(id = R.string.seat_price),
                style = textStyle16B()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.seat_reservation),
                style = textStyle12(color = MyGray)
            )
            Text(
                text = stringResource(id = R.string.guide_seat_reservation),
                style = textStyle16B()
            )
        }
    )
}

@Composable
fun RefundInfoContainer() {
    TicketGuideItem(
        title = stringResource(id = R.string.guide_refund),
        content = {
            Text(
                text = stringResource(id = R.string.guide_full_refund),
                style = textStyle12(color = MyYellow)
            )
            Text(
                text = stringResource(id = R.string.full_refund),
                style = textStyle16B()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.guide_partial_refund),
                style = textStyle12(color = MyYellow)
            )
            Text(
                text = stringResource(id = R.string.partial_refund),
                style = textStyle16B()
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.guide_no_refund),
                style = textStyle12(color = MyYellow)
            )
            Text(
                text = stringResource(id = R.string.no_refund),
                style = textStyle16B()
            )
        }
    )
}