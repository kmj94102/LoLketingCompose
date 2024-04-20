package com.example.lolketingcompose.ui.mypage

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.custom.GradeProgress
import com.example.lolketingcompose.ui.dialog.CashChargingDialog
import com.example.lolketingcompose.ui.dialog.CouponListDialog
import com.example.lolketingcompose.ui.dialog.LogoutDialog
import com.example.lolketingcompose.ui.dialog.PromotionDialog
import com.example.lolketingcompose.ui.dialog.WithdrawalDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.rememberLifecycleEvent
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle20B
import com.example.network.model.Grade
import com.example.network.model.MyInfo

@Composable
fun MyPageScreen(
    onBackClick: () -> Unit,
    goToModify: () -> Unit,
    goToPurchaseHistory: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isLogoutDialogShow by remember { mutableStateOf(false) }
    var isWithdrawalDialogShow by remember { mutableStateOf(false) }
    var isCashChargingDialogShow by remember { mutableStateOf(false) }
    var isCouponDialogShow by remember { mutableStateOf(false) }
    var isPromotionDialogShow by remember { mutableStateOf(false) }

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonHeader(
                title = "내 정보",
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            val myInfo = viewModel.myInfo.value
            Spacer(modifier = Modifier.height(15.dp))
            UserInfoContainer(
                myInfo = myInfo,
                goToModify = goToModify,
                updateGrade = {
                    viewModel.updateGrade(it)
                    isPromotionDialogShow = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 15.dp)
            )

            CashCouponContainer(
                myInfo = myInfo,
                cashCharging = { isCashChargingDialogShow = true },
                showCouponDialog = {
                    isCouponDialogShow = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
            )
            Spacer(modifier = Modifier.height(15.dp))

            SettingsContainer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                goToPurchaseHistory = goToPurchaseHistory,
                logout = { isLogoutDialogShow = true },
                withdrawal = { isWithdrawalDialogShow = true }
            )
        }
    )

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        if (lifecycleEvent == Lifecycle.Event.ON_RESUME) {
            viewModel.fetchMyInfo()
        }
    }

    val isLogout by viewModel.isLogout
    LaunchedEffect(isLogout) {
        if (isLogout) onBackClick()
    }

    LogoutDialog(
        isShow = isLogoutDialogShow,
        onDismiss = { isLogoutDialogShow = false },
        listener = { viewModel.logout() }
    )

    WithdrawalDialog(
        isShow = isWithdrawalDialogShow,
        onDismiss = { isWithdrawalDialogShow = false },
        listener = { viewModel.withdrawal() }
    )

    CashChargingDialog(
        isShow = isCashChargingDialogShow,
        myCash = viewModel.myInfo.value.cash,
        onDismiss = { isCashChargingDialogShow = false },
        listener = viewModel::updateCashCharging
    )

    CouponListDialog(
        isShow = isCouponDialogShow,
        availableList = viewModel.myInfo.value.getAvailableList(),
        usedList = viewModel.myInfo.value.getUsedList(),
        onDismiss = { isCouponDialogShow = false },
        useCoupon = viewModel::updateUsingCoupon
    )

    PromotionDialog(
        isShow = isPromotionDialogShow,
        grade = Grade.getGrade(viewModel.myInfo.value.grade),
        onDismiss = { isPromotionDialogShow = false }
    )
}

@Composable
fun UserInfoContainer(
    modifier: Modifier,
    myInfo: MyInfo,
    goToModify: () -> Unit,
    updateGrade: (String) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = Grade.getImage(myInfo.grade),
                contentDescription = null,
                modifier = Modifier
                    .size(90.dp)
                    .border(1.dp, MyWhite, CircleShape)
                    .padding(8.dp)
            )
            Text(
                text = Grade.getKoreanName(myInfo.grade),
                style = textStyle16B(color = MainColor),
                modifier = Modifier.padding(top = 5.dp)
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 15.dp)
        ) {
            Text(
                text = myInfo.nickname,
                style = textStyle20B(color = MainColor),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = myInfo.id,
                style = textStyle12(),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
            LeadingIconText(
                text = myInfo.formatMobileNumber().ifEmpty { "전화번호를 등록해 주세요" },
                iconRes = R.drawable.ic_phone,
                modifier = Modifier.fillMaxWidth()
            )
            LeadingIconText(
                text = myInfo.address.ifEmpty { "주소를 등록해주세요" },
                iconRes = R.drawable.ic_address,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_write),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Top)
                .nonRippleClickable(goToModify)
        )
    }

    GradeProgress(
        grade = myInfo.grade,
        value = myInfo.point,
        gradeUpdateListener = updateGrade,
        modifier = modifier
    )
}

@Composable
fun LeadingIconText(
    @DrawableRes
    iconRes: Int,
    text: String,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = text,
            style = textStyle12(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 5.dp)
        )
    }
}

@Composable
fun CashCouponContainer(
    modifier: Modifier,
    myInfo: MyInfo,
    cashCharging: () -> Unit,
    showCouponDialog: () -> Unit
) {
    Row(modifier = modifier) {
        CashCouponCard(
            title = "My 캐시",
            contents = myInfo.cash.formatWithComma().plus("원"),
            buttonText = "충전하기",
            modifier = Modifier.weight(1f),
            onClick = cashCharging
        )
        Spacer(modifier = Modifier.width(24.dp))

        CashCouponCard(
            title = "My 쿠폰",
            contents = "${myInfo.availableCoupons} / ${myInfo.totalCoupons}",
            buttonText = "상세보기",
            modifier = Modifier.weight(1f),
            onClick = showCouponDialog
        )
    }
}

@Composable
fun CashCouponCard(
    title: String,
    contents: String,
    buttonText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .border(1.dp, MainColor, RoundedCornerShape(10.dp))
    ) {
        Text(
            text = title,
            style = textStyle20B(color = MainColor, textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        Text(
            text = contents,
            style = textStyle16B(textAlign = TextAlign.Center),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 11.dp, horizontal = 7.dp)
        )
        Text(
            text = buttonText,
            style = textStyle16B(textAlign = TextAlign.Center),
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 7.dp)
                .padding(bottom = 7.dp)
                .background(MainColor, RoundedCornerShape(10.dp))
                .padding(vertical = 7.dp)
                .nonRippleClickable(onClick)
        )
    }
}

@Composable
fun SettingsContainer(
    modifier: Modifier,
    goToPurchaseHistory: () -> Unit,
    logout: () -> Unit,
    withdrawal: () -> Unit,
) {
    Column(
        modifier = modifier
            .border(1.dp, MyWhite, RoundedCornerShape(10.dp))
    ) {
        SettingItem("구매내역", goToPurchaseHistory)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyWhite)
        )

        SettingItem(stringResource(id = R.string.logout), logout)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyWhite)
        )

        SettingItem(stringResource(id = R.string.withdrawal), withdrawal)
    }
}

@Composable
fun SettingItem(
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .nonRippleClickable(onClick)
    ) {
        Text(
            text = text,
            style = textStyle16B(),
            modifier = Modifier
                .weight(1f)
                .padding(start = 15.dp, top = 16.dp, bottom = 16.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )
    }
}