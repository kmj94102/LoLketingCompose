package com.example.lolketingcompose.ui.guide

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.BaseStatus
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle20B

@Composable
fun GuideScreen(
    onBackClick: () -> Unit,
    goToDetail: (Int) -> Unit
) {
    HeaderBodyContainer(
        status = BaseStatus(),
        headerContent = {
            GuideHeader(onBackClick)
        },
        bodyContent = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(20.dp),
            ) {
                items(Guide.values()) {
                    GuideItem(
                        modifier = Modifier.fillMaxWidth(),
                        guide = it,
                        onClick = { goToDetail(it.titleRes) }
                    )
                }
            }
        }
    )
}

@Composable
fun GuideHeader(
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.img_guide),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(MyBlack.copy(alpha = 0.7f))
        )

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, start = 20.dp)
                .nonRippleClickable(onBackClick)
        )
        Text(
            text = "롤알못\n가이드",
            style = textStyle20B().copy(fontSize = 48.sp),
            modifier = Modifier.padding(top = 56.dp, start = 20.dp)
        )
    }
}

@Composable
fun GuideItem(
    modifier: Modifier = Modifier,
    guide: Guide,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MainColor),
        modifier = modifier
            .height(140.dp)
            .nonRippleClickable(onClick)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = guide.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .background(MyBlack.copy(alpha = 0.7f))
            )
            Text(
                text = guide.getTitle(context),
                style = textStyle20B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
    }
}