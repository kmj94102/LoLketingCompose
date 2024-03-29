package com.example.lolketingcompose.ui.news

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.HeaderBodyContainer
import com.example.lolketingcompose.ui.theme.MyGray
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle12
import com.example.lolketingcompose.util.textStyle16
import com.example.lolketingcompose.util.textStyle16B
import com.example.network.model.NewsContents
import de.charlex.compose.material3.HtmlText

@Composable
fun NewsScreen(
    onBackClick: () -> Unit,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HeaderBodyContainer(
        status = status,
        headerContent = {
            CommonHeader(title = "뉴스", onBackClick = onBackClick)
        },
        bodyContent = {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 30.dp),
            ) {
                itemsIndexed(viewModel.list) { index, item ->
                    NewsItem(
                        newsContents = item,
                        onClick = {
                            openWebsite(context, it)
                        }
                    )
                    if (viewModel.isMore.value && index == viewModel.list.size - 1) {
                        viewModel.fetchNews()
                    }
                }
            }
        }
    )
}

@Composable
fun NewsItem(
    newsContents: NewsContents,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 15.dp)
            .nonRippleClickable { onClick(newsContents.url) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            HtmlText(
                text = newsContents.title,
                style = textStyle16B(color = MyYellow),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 10.dp)
            )
            Text(
                text = newsContents.date,
                style = textStyle12(color = MyLightGray),
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        HtmlText(
            text = newsContents.description,
            style = textStyle16(),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )
    }
    Divider(color = MyGray)
}

fun openWebsite(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = android.net.Uri.parse(url)
    }
    context.startActivity(intent)
}
