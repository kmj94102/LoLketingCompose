package com.example.lolketingcompose.structure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.theme.SubColor
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle14B
import com.example.lolketingcompose.util.textStyle16

@Composable
fun NetworkErrorScreen(
    onBackClick: (() -> Unit)? = null,
    reload: () -> Unit,
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CommonLottieAnimation(
                resId = R.raw.lottie_error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            Text(
                text = stringResource(id = R.string.network_error_message),
                style = textStyle16(),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.reload),
                style = textStyle14B(),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SubColor, RoundedCornerShape(15.dp))
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .nonRippleClickable(reload)
            )
        }
        onBackClick?.let {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp, start = 20.dp)
                    .nonRippleClickable(it)
            )
        }
    }
}