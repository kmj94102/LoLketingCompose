package com.example.lolketingcompose.ui.shop.purchase

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.shop.CartBadge
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.textStyle20

@Composable
fun PurchaseScreen(
    onBackClick: () -> Unit,
    viewModel: PurchaseViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    TopBodyBottomContainer(
        status = status,
        topContent = {
            CommonHeader(
                onBackClick = onBackClick,
                tailIcon = {
                    CartBadge(
                        count = 1,
                        onClick = {}
                    )
                }
            )
        },
        bodyContent = {},
        bottomContent = {}
    )
}

@Composable
fun ShippingInformation(
    goToAddress: () -> Unit
) {
    Text(
        text = "배송 정보",
        style = textStyle16B(),
        modifier = Modifier.padding(top = 15.dp, start = 20.dp, bottom = 10.dp)
    )

    CommonTextField(
        value = "",
        onTextChange = {  },
        hint = "주문자의 이름을 입력해주세요",
        imeAction = ImeAction.Next,
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_user),
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth()
    )

    CommonTextField(
        value = "",
        onTextChange = {  },
        hint = "전화번호를 입력해주세요",
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_phone),
                contentDescription = null
            )
        },
        modifier = Modifier.fillMaxWidth()
    )

    Card(
        shape = RoundedCornerShape(3.dp),
        border = BorderStroke(1.dp, MyWhite),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = Modifier
            .fillMaxWidth()
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
                text = "".ifEmpty { "주소를 입력해주세요" },
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