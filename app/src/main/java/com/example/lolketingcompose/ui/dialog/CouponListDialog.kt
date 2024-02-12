package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle22B
import com.example.network.model.Coupon

@Composable
fun CouponListDialog(
    isShow: Boolean,
    availableList: List<Coupon>,
    usedList: List<Coupon>,
    onDismiss: () -> Unit,
    useCoupon: (Int) -> Unit
) {
    var isAvailable by remember { mutableStateOf(true) }

    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        title = {
            Text(
                text = "쿠폰함",
                style = textStyle22B(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
        },
        contents = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(3.dp),
                    colors = CardDefaults.cardColors(containerColor = MyGray)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "사용 가능",
                            style = textStyle16B(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .weight(1f)
                                .background(if (isAvailable) MainColor else Color.Transparent)
                                .padding(vertical = 5.dp)
                                .nonRippleClickable { isAvailable = true }
                        )
                        Text(
                            text = "사용 완료",
                            style = textStyle16B(textAlign = TextAlign.Center),
                            modifier = Modifier
                                .weight(1f)
                                .background(if (isAvailable) Color.Transparent else MainColor)
                                .padding(vertical = 5.dp)
                                .nonRippleClickable { isAvailable = false }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                        .background(MyGray, RoundedCornerShape(3.dp))
                        .border(1.dp, MyWhite, RoundedCornerShape(3.dp))
                ) {
                    if (isAvailable) {
                        items(availableList) {
                            CouponItem(
                                modifier = Modifier.fillMaxWidth(),
                                coupon = it,
                                isAvailable = isAvailable,
                                useCoupon = useCoupon
                            )
                        }
                        if (availableList.isEmpty()) {
                            item {
                                CouponListEmptyContainer(
                                    "사용하지 않은\n쿠폰 내역이 없습니다."
                                )
                            }
                        }
                    } else {
                        items(usedList) {
                            CouponItem(
                                modifier = Modifier.fillMaxWidth(),
                                coupon = it,
                                isAvailable = isAvailable,
                            )
                        }
                        if (usedList.isEmpty()) {
                            item {
                                CouponListEmptyContainer(
                                    "사용한 쿠폰 내역이 없습니다."
                                )
                            }
                        }
                    }
                }
            }
        },
        isSingleButton = true,
        okClick = onDismiss
    )
}

@Composable
fun CouponItem(
    modifier: Modifier = Modifier,
    coupon: Coupon,
    isAvailable: Boolean,
    useCoupon: (Int) -> Unit = {},
) {
    Column {
        Row(modifier = modifier.padding(10.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = coupon.getCouponName(),
                    style = textStyle16(),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = coupon.timestamp.replace("T", " "),
                    style = textStyle12(color = MyLightGray),
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Text(
                text = "${coupon.rp}RP\n받기",
                style = textStyle12().copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .width(67.dp)
                    .background(
                        if (isAvailable) MainColor else MyLightGray,
                        RoundedCornerShape(3.dp)
                    )
                    .padding(vertical = 2.dp)
                    .nonRippleClickable { useCoupon(coupon.id) }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyLightGray)
        )
    }
}

@Composable
fun CouponListEmptyContainer(
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
    ) {
        Text(
            text = text,
            style = textStyle16(color = MyLightGray, textAlign = TextAlign.Center)
        )
    }
}