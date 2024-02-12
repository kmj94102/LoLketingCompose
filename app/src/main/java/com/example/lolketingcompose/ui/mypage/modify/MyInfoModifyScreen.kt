package com.example.lolketingcompose.ui.mypage.modify

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle20

@Composable
fun MyInfoModifyScreen(
    address: String?,
    onBackClick: () -> Unit,
    goToAddress: () -> Unit,
    viewModel: MyInfoModifyViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsState()

    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            CommonHeader(title = "내 정보 수정", onBackClick = onBackClick)
        },
        bodyContent = {
            MyInfoModifyBody(
                viewModel = viewModel,
                goToAddress = goToAddress
            )
        },
        bottomContent = {
            CommonButton(
                text = "수정하기",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable(viewModel::updateMyInfo)
            )
        }
    )

    address?.let(viewModel::updateAddress)
}

@Composable
fun MyInfoModifyBody(
    viewModel: MyInfoModifyViewModel,
    goToAddress: () -> Unit
) {
    val info = viewModel.modifyInfo.value
    Column(
        modifier = Modifier.padding(top = 16.dp, start = 30.dp, end = 30.dp)
    ) {
        CommonTextField(
            value = info.nickname,
            onTextChange = { viewModel.updateInfo(info.copy(nickname = it)) },
            hint = "닉네임을 입력해주세요",
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_user),
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MyGray)
            )
            Text(
                text = "선택",
                style = textStyle12(color = MyGray),
                modifier = Modifier.padding(horizontal = 4.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(MyGray)
            )
        }

        CommonTextField(
            value = info.mobile,
            onTextChange = { value ->
                if (value.all { it.isDigit() } && value.length < 12) {
                    viewModel.updateInfo(info.copy(mobile = value))
                }
            },
            hint = "전화번호를 입력해주세요",
            keyboardType = KeyboardType.Number,
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
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
                    text = info.address.ifEmpty { "주소를 입력해주세요" },
                    style = textStyle20(
                        if (info.address.isEmpty()) MyGray else MyWhite
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
            }
        }
    }
}