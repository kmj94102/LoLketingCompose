package com.example.lolketingcompose.ui.login.address

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle20

@Composable
fun AddressScreen(
    viewModel: AddressViewModel = hiltViewModel(),
    onBackClick: (String) -> Unit
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    HeaderBodyContainer(
        status = status,
        headerContent = {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 16.dp, start = 20.dp)
                    .nonRippleClickable { onBackClick("") }
            )
        },
        bodyContent = {
            Spacer(modifier = Modifier.height(16.dp))
            AddressBody(viewModel = viewModel, modifier = Modifier.weight(1f))
            CommonButton(
                text = "주소 설정",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable {
                        viewModel.info.value.getFullAddress()
                            .onSuccess(onBackClick)
                            .onFailure { status.updateMessage("주소를 입력해주세요.") }
                    }
            )
        }
    )
}

@Composable
fun AddressBody(
    modifier: Modifier = Modifier,
    viewModel: AddressViewModel
) {
    Column(modifier = modifier) {
        CommonTextField(
            value = viewModel.info.value.keyword,
            onTextChange = viewModel::updateKeyword,
            hint = "지번, 도로명, 건물명으로 검색",
            leadingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            },
            imeAction = ImeAction.Search,
            onSearch = { viewModel.fetchAddressList() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        if (viewModel.info.value.isSearchMode) {
            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyGray)
            )
            if (viewModel.list.isEmpty()) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "지번, 도로명, 건물명으로\n검색해보세요",
                    style = textStyle20(MyGray).copy(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(viewModel.list) {
                        AddressItem(
                            address = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .nonRippleClickable { viewModel.setAddress(it) }
                        )
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            CommonTextField(
                value = viewModel.info.value.addressDetail,
                onTextChange = viewModel::updateAddressDetail,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_address),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun AddressItem(
    modifier: Modifier = Modifier,
    address: String
) {
    Column(modifier = modifier.padding(horizontal = 20.dp)) {
        Text(
            text = address,
            style = textStyle16(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MyGray)
        )
    }
}