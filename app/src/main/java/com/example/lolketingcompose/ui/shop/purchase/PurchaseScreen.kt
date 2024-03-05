package com.example.lolketingcompose.ui.shop.purchase

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.database.GoodsEntity
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.dialog.CashChargingDialog
import com.example.lolketingcompose.ui.dialog.PurchaseDeleteInfoDialog
import com.example.lolketingcompose.ui.dialog.PurchaseDialog
import com.example.lolketingcompose.ui.shop.AmountSelector
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle14B
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle20
import com.example.network.model.PurchaseInfo

@Composable
fun PurchaseScreen(
    onBackClick: () -> Unit,
    goToShop: () -> Unit,
    goToAddress: () -> Unit,
    newAddress: String?,
    viewModel: PurchaseViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isDeleteDialogShow by remember { mutableStateOf(false) }
    var isPurchaseDialogShow by remember { mutableStateOf(false) }
    var isCashChargingDialogShow by remember { mutableStateOf(false) }

    newAddress?.let(viewModel::updateAddress)

    TopBodyBottomContainer(
        status = status,
        onBackClick = goToShop,
        topContent = {
            CommonHeader(
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            ShippingInformation(
                goToAddress = goToAddress,
                purchaseInfo = viewModel.purchaseInfo.value
            )
            Divider(color = MyGray, modifier = Modifier.padding(top = 15.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                itemsIndexed(viewModel.list) { index, item ->
                    PurchaseItem(
                        item = item,
                        updateAmount = {
                            viewModel.updateAmount(index, it)
                        },
                        onDeleteClick = {
                            if (viewModel.list.size <= 1) {
                                isDeleteDialogShow = true
                            } else {
                                viewModel.deleteItem(index)
                            }
                        }
                    )
                }
            }
            Row(modifier = Modifier.padding(vertical = 15.dp, horizontal = 20.dp)) {
                Text(text = "총 합계", style = textStyle16B())
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = viewModel.totalPrice.formatWithComma().plus("원"),
                    style = textStyle16B(color = MainColor)
                )
            }
        },
        bottomContent = {
            CommonButton(
                text = "결제하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable {
                        if (viewModel.purchaseInfo.value.cash > viewModel.totalPrice) {
                            isPurchaseDialogShow = true
                        } else {
                            isCashChargingDialogShow = true
                        }
                    }
            )
        }
    )

    PurchaseDeleteInfoDialog(
        isShow = isDeleteDialogShow,
        onDismiss = { isDeleteDialogShow = false },
        okClick = onBackClick
    )

    PurchaseDialog(
        isShow = isPurchaseDialogShow,
        totalPrice = viewModel.totalPrice,
        myCash = viewModel.purchaseInfo.value.cash,
        onDismiss = { isPurchaseDialogShow = false },
        okClick = viewModel::purchase
    )

    CashChargingDialog(
        isShow = isCashChargingDialogShow,
        myCash = viewModel.purchaseInfo.value.cash,
        onDismiss = { isCashChargingDialogShow = false },
        listener = viewModel::cashCharging
    )
}

@Composable
fun ShippingInformation(
    goToAddress: () -> Unit,
    purchaseInfo: PurchaseInfo
) {
    Text(
        text = "배송 정보",
        style = textStyle16B(),
        modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
    )

    CommonTextField(
        value = purchaseInfo.nickname,
        onTextChange = { },
        hint = "주문자의 이름을 입력해주세요",
        imeAction = ImeAction.Next,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))

    CommonTextField(
        value = purchaseInfo.mobile,
        onTextChange = { },
        hint = "전화번호를 입력해주세요",
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_phone),
                contentDescription = null
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    )
    Spacer(modifier = Modifier.height(10.dp))

    Card(
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(1.dp, MyWhite),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 16.dp)
            .nonRippleClickable(goToAddress)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 9.dp, vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_address),
                contentDescription = null,
            )
            Text(
                text = purchaseInfo.address.ifEmpty { "주소를 입력해주세요" },
                style = textStyle20(
                    if ("info.address".isEmpty()) MyGray else MyWhite
                ),
                maxLines = 1,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun PurchaseItem(
    item: GoodsEntity,
    updateAmount: (Int) -> Unit,
    onDeleteClick: () -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        val (image, category, name, amountSelector, price, delete) = createRefs()

        AsyncImage(
            model = item.image,
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, MainColor, RoundedCornerShape(10.dp))
                .background(MyWhite, RoundedCornerShape(10.dp))
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Text(
            text = "[${item.category}]",
            style = textStyle12(color = MyGray),
            modifier = Modifier.constrainAs(category) {
                top.linkTo(parent.top)
                bottom.linkTo(name.top)
                start.linkTo(image.end, 10.dp)
            }
        )

        Text(
            text = item.name,
            style = textStyle16B(),
            modifier = Modifier.constrainAs(name) {
                top.linkTo(category.bottom, 6.dp)
                bottom.linkTo(amountSelector.top, 6.dp)
                start.linkTo(category.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        AmountSelector(
            value = item.amount,
            onMinusClick = { updateAmount(item.amount - 1) },
            onPlusClick = { updateAmount(item.amount + 1) },
            modifier = Modifier.constrainAs(amountSelector) {
                top.linkTo(name.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(category.start)
            }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_trash),
            contentDescription = null,
            modifier = Modifier
                .nonRippleClickable(onDeleteClick)
                .constrainAs(delete) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                }
        )

        Text(
            text = (item.price * item.amount).formatWithComma().plus("원"),
            style = textStyle14B(color = MainColor, textAlign = TextAlign.End),
            modifier = Modifier.constrainAs(price) {
                bottom.linkTo(parent.bottom)
                start.linkTo(amountSelector.end)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
    Divider(color = MyGray)
}