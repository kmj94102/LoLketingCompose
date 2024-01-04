package com.example.lolketingcompose.ui.home.login

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.custom.CommonTextField
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle20B

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_lolketing_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 117.dp)
                .size(188.dp, 98.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            CommonTextField(
                value = viewModel.info.value.id,
                onTextChange = viewModel::updateId,
                hint = "아이디를 입력해주세요",
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = null
                    )
                },
                imeAction = ImeAction.Next,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            CommonTextField(
                value = viewModel.info.value.password,
                onTextChange = viewModel::updatePw,
                visualTransformation = PasswordVisualTransformation(),
                hint = "비밀번호를 입력해주세요",
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_pw),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            CommonButton(
                text = "로그인",
                shape = RoundedCornerShape(3.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(top = 22.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "비밀번호 찾기", style = textStyle16(MainColor))
                Text(text = "회원가입", style = textStyle16(MainColor))
            }
            Box(
                modifier = Modifier
                    .padding(top = 22.dp, bottom = 16.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MyWhite)
            )

            SocialLoginButton(
                text = "네이버 로그인",
                textColor = MyWhite,
                backgroundColor = Color(0xFF03C75A),
                iconRes = R.drawable.img_logo_naver,
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable { viewModel.naverLogin(context) }
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            SocialLoginButton(
                text = "네이버 로그인",
                textColor = MyWhite,
                backgroundColor = Color(0xFF03C75A),
                iconRes = R.drawable.img_logo_naver,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun SocialLoginButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color,
    backgroundColor: Color,
    @DrawableRes
    iconRes: Int
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(3.dp))
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp)
                .height(40.dp)
        )

        Text(
            text = text,
            style = textStyle20B(textColor),
            modifier = Modifier
                .align(Alignment.Center)
                .padding(vertical = 10.dp)
        )
    }
}