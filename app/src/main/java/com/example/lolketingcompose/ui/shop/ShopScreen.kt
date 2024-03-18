package com.example.lolketingcompose.ui.shop

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyBlack
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.formatWithComma
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle12B
import com.example.lolketingcompose.util.textStyle14B
import com.example.network.model.Goods

@Composable
fun ShopScreen(
    onBackClick: () -> Unit,
    goToDetail: (Int) -> Unit,
    goToCart: () -> Unit,
    viewModel: ShopViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    HeaderBodyContainer(
        status = status,
        headerContent = {
            val cartCount by viewModel.cartCount.collectAsStateWithLifecycle()
            ShopHeader(
                cartCount = cartCount,
                onBackClick = onBackClick,
                goToCart = goToCart
            )
        },
        bodyContent = {
            ScrollableTabRow(
                selectedTabIndex = viewModel.selectedIndex.value,
                containerColor = MyBlack,
                edgePadding = 0.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        color = MainColor,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[viewModel.selectedIndex.value])
                    )
                },
            ) {
                viewModel.tabList.forEachIndexed { index, item ->
                    Tab(
                        selected = index == viewModel.selectedIndex.value,
                        onClick = { viewModel.updateTabIndex(index) },
                        text = { Text(text = item) },
                        selectedContentColor = MainColor,
                        unselectedContentColor = MyWhite
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(15.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(top = 15.dp, bottom = 30.dp),
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                items(viewModel.itemList) {
                    ShopItem(
                        item = it,
                        onClick = goToDetail
                    )
                }
            }
        }
    )
}

@Composable
fun ShopHeader(
    cartCount: Int,
    onBackClick: () -> Unit,
    goToCart: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(MyBlack)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_shop),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(250.dp)
                .align(Alignment.BottomEnd)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 16.dp, start = 20.dp)
                .size(24.dp)
                .nonRippleClickable(onBackClick)
        )
        CartBadge(
            count = cartCount,
            onClick = goToCart,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp)
        )
        Text(
            text = "굿즈 쇼핑",
            style = textStyle14B().copy(fontSize = 40.sp),
            modifier = Modifier.padding(top = 56.dp, start = 20.dp)
        )
    }
}

@Composable
fun CartBadge(
    modifier: Modifier = Modifier,
    count: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(end = 20.dp)
            .size(24.dp, 20.dp)
            .nonRippleClickable(onClick)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp, 18.dp)
                .align(Alignment.BottomCenter)
        )
        if (count > 0) {
            Text(
                text = if (count < 10) "$count" else "9+",
                style = textStyle12B(textAlign = TextAlign.Center),
                modifier = Modifier
                    .size(13.dp)
                    .background(MainColor, CircleShape)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun ShopItem(
    item: Goods,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MyGray, RoundedCornerShape(10.dp))
            .padding(5.dp)
            .padding(bottom = 5.dp)
            .nonRippleClickable { onClick(item.goodsId) }
    ) {
        AsyncImage(
            model = item.url,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(123.dp)
                .background(MyWhite, RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
                .clip(RoundedCornerShape(topStart = 7.dp, topEnd = 7.dp))
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(text = "[${item.category}]", style = textStyle12(color = MyGray))
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = item.name,
            style = textStyle12(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))

        Text(text = item.price.formatWithComma().plus("원"), style = textStyle14B(MyYellow))
    }
}