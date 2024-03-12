package com.example.lolketingcompose.structure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.example.lolketingcompose.R

@Composable
fun LoadingScreen(isShow: Boolean) {
    if (isShow) {
        Dialog(onDismissRequest = {}) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CommonLottieAnimation(
                    resId = R.raw.lottie_loading,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}