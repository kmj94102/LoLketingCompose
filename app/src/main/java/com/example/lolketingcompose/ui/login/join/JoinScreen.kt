package com.example.lolketingcompose.ui.login.join

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.BaseContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle20

@Composable
fun JoinScreen(
    viewModel: JoinViewModel = hiltViewModel(),
    address: String?,
    onBack: () -> Unit,
    goToAddress: () -> Unit,
    goToHome: () -> Unit
) {
    val status by viewModel.status.collectAsStateWithLifecycle()

    BaseContainer(status = status) {
        Column(modifier = Modifier.fillMaxWidth()) {
            JoinHeader(onBack, viewModel.guide.value)
            JoinBody(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .weight(1f),
                viewModel = viewModel,
                goToAddress = goToAddress
            )
            CommonButton(
                text = "회원가입",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable(viewModel::join))
        }
    }

    address?.let(viewModel::updateAddress)

    LaunchedEffect(viewModel.complete.value) {
        if (viewModel.complete.value) {
            goToHome()
        }
    }

}

@Composable
fun ColumnScope.JoinHeader(
    onBack: () -> Unit,
    guide: String
) {
    Image(
        painter = painterResource(id = R.drawable.ic_back),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 16.dp, start = 20.dp)
            .nonRippleClickable(onBack)
    )
    Image(
        painter = painterResource(id = R.drawable.img_lolketing_logo),
        contentDescription = null,
        modifier = Modifier
            .padding(top = 77.dp)
            .size(188.dp, 98.dp)
            .align(Alignment.CenterHorizontally)
    )
    Text(
        text = guide,
        style = textStyle16(MyYellow).copy(textAlign = TextAlign.Center),
        modifier = Modifier
            .padding(vertical = 25.dp, horizontal = 20.dp)
            .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun JoinBody(
    modifier: Modifier = Modifier,
    viewModel: JoinViewModel,
    goToAddress: () -> Unit
) {
    val info = viewModel.info.value
    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
    ) {
        item {
            CommonTextField(
                value = info.id,
                onTextChange = { value ->
                    info.copy(id = value).let {
                        viewModel.updateField { it }
                        if (it.isEmailValid().not()) {
                            viewModel.updateGuide(context.getString(R.string.guide_id_format))
                        } else {
                            viewModel.updateGuide("")
                        }
                    }
                },
                isError = info.id.isNotEmpty() && info.isEmailValid().not(),
                readOnly = viewModel.readOnly.value,
                hint = "아이디를 입력해주세요",
                imeAction = ImeAction.Next,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            if (viewModel.isEmail.value) {
                CommonTextField(
                    value = info.password,
                    onTextChange = { value ->
                        info.copy(password = value).let {
                            viewModel.updateField { it }
                            if (it.isPasswordValid().not()) {
                                viewModel.updateGuide(context.getString(R.string.guide_password))
                            } else if (it.password.length < 6) {
                                viewModel.updateGuide(
                                    context.getString(R.string.guide_password_length)
                                )
                            } else {
                                viewModel.updateGuide("")
                            }
                        }
                    },
                    isError = info.password.isNotEmpty() && info.isPasswordValid().not()
                            && info.password.length !in 6..30,
                    imeAction = ImeAction.Next,
                    visualTransformation = PasswordVisualTransformation(),
                    hint = "비밀번호를 입력해주세요",
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pw),
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )

                CommonTextField(
                    value = info.passwordCheck,
                    onTextChange = { value ->
                        info.copy(passwordCheck = value).let {
                            viewModel.updateField { it }
                            if (it.password != it.passwordCheck) {
                                viewModel.updateGuide(context.getString(R.string.guide_password_check))
                            } else {
                                viewModel.updateGuide("")
                            }
                        }
                    },
                    isError = info.passwordCheck.isNotEmpty() && info.password != info.passwordCheck,
                    imeAction = ImeAction.Next,
                    visualTransformation = PasswordVisualTransformation(),
                    hint = "비밀번호를 다시 입력해주세요",
                    leadingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pw),
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }

            CommonTextField(
                value = info.nickname,
                onTextChange = {
                    viewModel.updateField { info.copy(nickname = it) }
                    if (it.length >= 11) {
                        viewModel.updateGuide(context.getString(R.string.guide_nickname_length))
                    } else {
                        viewModel.updateGuide("")
                    }
                },
                isError = info.id.isNotEmpty() && info.nickname.length >= 11,
                hint = "닉네임을 입력해주세요",
                imeAction = ImeAction.Next,
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp)
                        .background(MyGray)
                )
                Text(
                    text = "선택",
                    style = textStyle12(MyGray),
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
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
                        viewModel.updateField { info.copy(mobile = value) }
                    }
                    if (value.length !in 10..11) {
                        viewModel.updateGuide(context.getString(R.string.guide_mobile_length))
                    } else {
                        viewModel.updateGuide("")
                    }
                },
                isError = info.mobile.isNotEmpty() && info.mobile.length !in 10..11,
                keyboardType = KeyboardType.Phone,
                hint = "전화번호를 입력해주세요",
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_phone),
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
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
                        text = viewModel.info.value.address.ifEmpty { "주소를 입력해주세요" },
                        style = textStyle20(
                            if (viewModel.info.value.address.isEmpty()) MyGray else MyWhite
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
}