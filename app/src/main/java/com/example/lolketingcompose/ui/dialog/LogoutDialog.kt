package com.example.lolketingcompose.ui.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.lolketingcompose.R

@Composable
fun LogoutDialog(
    isShow: Boolean,
    onDismiss: () -> Unit,
    listener: () -> Unit
) {
    ConfirmDialog(
        isShow = isShow,
        content = stringResource(id = R.string.guide_logout),
        onDismiss = onDismiss,
        okClick = {
            listener()
            onDismiss()
        }
    )
}