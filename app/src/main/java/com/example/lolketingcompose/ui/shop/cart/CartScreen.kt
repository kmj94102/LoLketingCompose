package com.example.lolketingcompose.ui.shop.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.database.GoodsEntity
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.shop.AmountSelector
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle14
import com.example.lolketingcompose.util.textStyle14B
import com.example.lolketingcompose.util.textStyle16B

@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    viewModel: CartViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    TopBodyBottomContainer(
        status = status,
        topContent = {
            CommonHeader(title = "장바구니", onBackClick = onBackClick)
        },
        bodyContent = {
            CartBodyContainer(viewModel = viewModel)
        },
        bottomContent = {
            CommonButton(
                text = "선택 상품 구매하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable { }
            )
        }
    )
}

@Composable
fun ColumnScope.CartBodyContainer(
    viewModel: CartViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 20.dp)
    ) {
        Checkbox(
            checked = viewModel.isAllChecked,
            onCheckedChange = { viewModel.updateCheckedStatusAll() },
            colors = CheckboxDefaults.colors(
                checkedColor = MainColor,
                uncheckedColor = MyGray
            )
        )

        Text(
            text = "전체 선택",
            style = textStyle14(),
            modifier = Modifier
                .nonRippleClickable { viewModel.updateCheckedStatusAll() }
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "선택 삭제",
            style = textStyle14(color = MyYellow),
            modifier = Modifier.nonRippleClickable(viewModel::deleteItems)
        )
    }
    Divider(color = MyGray)

    LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
        items(viewModel.list) {
            CartItem(
                info = it,
                onCheckedChange = viewModel::updateCheckedStatus,
                onAmountChange = viewModel::updateAmount
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
}

@Composable
fun CartItem(
    info: GoodsEntity,
    onCheckedChange: (Int, Boolean) -> Unit,
    onAmountChange: (Int, Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 5.dp, top = 15.dp, bottom = 15.dp)
    ) {
        AsyncImage(
            model = info.image,
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MyWhite, RoundedCornerShape(10.dp))
                .border(1.dp, MainColor, RoundedCornerShape(10.dp))
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        ) {
            Text(text = info.name, style = textStyle16B(), modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = (info.price * info.amount).formatWithComma().plus("원"),
                style = textStyle14B(color = MainColor),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))

            AmountSelector(
                value = info.amount,
                onMinusClick = { onAmountChange(info.index, info.amount - 1) },
                onPlusClick = { onAmountChange(info.index, info.amount + 1) }
            )
        }

        Checkbox(
            checked = info.isChecked,
            onCheckedChange = { onCheckedChange(info.index, info.isChecked.not()) },
            colors = CheckboxDefaults.colors(
                checkedColor = MainColor,
                uncheckedColor = MyGray
            )
        )
    }
    Divider(color = MyGray)
}