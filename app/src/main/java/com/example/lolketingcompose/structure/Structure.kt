package com.example.lolketingcompose.structure

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle16B
import com.example.lolketingcompose.util.toast
import kotlinx.coroutines.delay

@Composable
fun BaseStructureScreen(
    status: BaseStatus,
    onBackClick: (() -> Unit)? = null,
    errorScreen: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    var isShow by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MyBlack)
    ) {
        if (status.isNetworkError) {
            errorScreen()
        } else {
            content()
        }
    }

    LaunchedEffect (status.isLoading) {
        isShow = if (status.isLoading) {
            delay(1000)
            status.isLoading
        } else {
            false
        }
    }
    LoadingScreen(isShow)

    LaunchedEffect(status.message) {
        val text = getStatusMessage(context, status.message)
        if (text.trim().isEmpty()) return@LaunchedEffect
        context.toast(text)

        delay(500)
        status.updateMessage("")
    }

    LaunchedEffect(status.isFinish) {
        if (status.isFinish) onBackClick?.invoke()
    }
}

private fun getStatusMessage(context: Context, message: Any): String = runCatching {
    when (message) {
        is String -> message
        is Int -> context.getString(message)
        else -> ""
    }
}.getOrElse { "" }

@Composable
fun BaseContainer(
    status: BaseStatus,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    reload: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    errorScreen: (@Composable () -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    BaseStructureScreen(
        status = status,
        onBackClick = onBackClick,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                content()
            }
        },
        errorScreen = {
            if (errorScreen != null)
                errorScreen()
            else {
                NetworkErrorScreen(
                    onBackClick = onBackClick,
                    reload = { reload?.invoke() }
                )
            }
        }
    )
}

@Composable
fun HeaderBodyContainer(
    status: BaseStatus,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    reload: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    errorScreen: (@Composable () -> Unit)? = null,
    headerContent: @Composable ColumnScope.() -> Unit,
    bodyContent: @Composable ColumnScope.() -> Unit
) {
    BaseContainer(
        status = status,
        paddingValues = paddingValues,
        reload = reload,
        onBackClick = onBackClick,
        errorScreen = errorScreen
    ) {
        headerContent()
        Column(modifier = Modifier.weight(1f)) {
            bodyContent()
        }
    }
}

@Composable
fun CommonHeader(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    title: String = "",
    centerItem: @Composable () -> Unit = {
        Text(text = title, style = textStyle16B(MainColor))
    },
    tailIcon: @Composable () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp, bottom = 16.dp)
                .nonRippleClickable(onBackClick)
        )
        Box(modifier = Modifier.align(Alignment.Center)) {
            centerItem()
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            tailIcon()
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(1.dp)
                .background(MyGray)
        )
    }
}

@Composable
fun TopBodyBottomContainer(
    status: BaseStatus,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    reload: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    errorScreen: (@Composable () -> Unit)? = null,
    topContent: @Composable ColumnScope.() -> Unit,
    bodyContent: @Composable ColumnScope.() -> Unit,
    bottomContent: @Composable ColumnScope.() -> Unit,
) {
    BaseContainer(
        status = status,
        paddingValues = paddingValues,
        reload = reload,
        onBackClick = onBackClick,
        errorScreen = errorScreen
    ) {
        topContent()
        Column(modifier = Modifier.weight(1f)) {
            bodyContent()
        }
        bottomContent()
    }
}