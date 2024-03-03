package com.example.lolketingcompose.ui.shop.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.dialog.CartAddSuccessDialog
import com.example.lolketingcompose.ui.shop.AmountSelector
import com.example.lolketingcompose.ui.shop.CartBadge
import com.example.lolketingcompose.ui.shop.ImageSlider
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle14
import com.example.lolketingcompose.util.textStyle20B
import com.example.network.model.GoodsDetail

@Composable
fun ShopDetailScreen(
    onBackClick: () -> Unit,
    goToCart: () -> Unit,
    goToPurchase: () -> Unit,
    viewModel: ShopDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    var isShow by remember { mutableStateOf(false) }

    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()
            CommonHeader(
                onBackClick = onBackClick,
                tailIcon = {
                    CartBadge(
                        count = cartCount,
                        onClick = goToCart
                    )
                }
            )
        },
        bodyContent = {
            ShopDetailBodyContainer(
                viewModel.item.value,
                viewModel.amount.value,
                viewModel::updateAmount,
                modifier = Modifier.fillMaxSize()
            )
        },
        bottomContent = {
            Row {
                CommonButton(
                    text = "장바구니 담기",
                    color = MyGray,
                    modifier = Modifier
                        .weight(1f)
                        .nonRippleClickable {
                            viewModel.insertCart(
                                onSuccess = { isShow = true }
                            )
                        }
                )
                CommonButton(text = "바로구매", modifier = Modifier
                    .weight(1f)
                    .nonRippleClickable { goToPurchase() }
                )
            }
        }
    )

    CartAddSuccessDialog(
        isShow = isShow,
        onDismiss = { isShow = false },
        okClick = onBackClick,
        cancelClick = goToCart
    )
}

@Composable
fun ShopDetailBodyContainer(
    info: GoodsDetail,
    amount: Int,
    updateAmount: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp),
        modifier = modifier
    ) {
        item {
            ImageSlider(
                imageList = info.imageList
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "[${info.category}]",
                style = textStyle14(color = MyGray),
                modifier = Modifier.padding(start = 20.dp)
            )
            Text(
                text = info.name,
                style = textStyle20B(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                AmountSelector(
                    value = amount,
                    onMinusClick = { updateAmount(-1) },
                    onPlusClick = { updateAmount(1) }
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = (info.price * amount).formatWithComma().plus("원"),
                    style = textStyle20B(color = MainColor)
                )
            }
        }
    }
}