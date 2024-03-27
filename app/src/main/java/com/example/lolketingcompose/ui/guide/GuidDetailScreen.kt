package com.example.lolketingcompose.ui.guide

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.util.textStyle16
import de.charlex.compose.material3.HtmlText

@Composable
fun GuidDetailScreen(
    onBackClick: () -> Unit,
    viewModel: GuidDetailViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HeaderBodyContainer(
        status = status,
        onBackClick = onBackClick,
        headerContent = {
            CommonHeader(
                title = viewModel.guide.value.getTitle(context),
                onBackClick = onBackClick
            )
        },
        bodyContent = {
            when (viewModel.guide.value) {
                Guide.Terms -> {
                    TermsItem()
                }

                else -> {
                    GuidDetailItem(viewModel.guide.value)
                }
            }
        }
    )
}

@Composable
fun GuidDetailItem(guide: Guide) {
    LazyColumn {
        item {
            Image(
                painter = painterResource(id = guide.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )
        }
        item {
            HtmlText(
                text = stringResource(id = guide.content),
                style = textStyle16(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)
            )
        }
    }
}

@Composable
fun TermsItem() {
    val imageList = listOf(
        R.drawable.img_lol_guide_term,
        R.drawable.img_terms01,
        R.drawable.img_terms02,
        R.drawable.img_terms03,
        R.drawable.img_terms04,
        R.drawable.img_terms05,
        R.drawable.img_terms06,
        R.drawable.img_terms07,
        R.drawable.img_terms08,
        R.drawable.img_terms09,
        R.drawable.img_terms10,
        R.drawable.img_terms11,
        R.drawable.img_terms12,
        R.drawable.img_terms13,
        R.drawable.img_terms14,
    )

    LazyColumn {
        items(imageList) {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}