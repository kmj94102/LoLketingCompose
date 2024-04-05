package com.example.lolketingcompose.ui.board.write

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.lolketingcompose.R
import com.example.lolketingcompose.structure.CommonHeader
import com.example.lolketingcompose.structure.TopBodyBottomContainer
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.custom.RestrictedTextField
import com.example.lolketingcompose.ui.dialog.TeamSelectDialog
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyRed
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.util.dashedBorder
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle20
import com.example.lolketingcompose.util.textStyle20B

@Composable
fun BoardWriteScreen(
    onBackClick: () -> Unit,
    viewModel: BoardWriteViewModel = hiltViewModel()
) {
    val status by viewModel.status.collectAsStateWithLifecycle()
    TopBodyBottomContainer(
        status = status,
        onBackClick = onBackClick,
        topContent = {
            CommonHeader(onBackClick = onBackClick, title = "게시글 등록")
        },
        bodyContent = {
            BoardWriteBody(viewModel)
        },
        bottomContent = {
            CommonButton(
                text = "등록",
                modifier = Modifier
                    .fillMaxWidth()
                    .nonRippleClickable { viewModel.insertBoard() }
            )
        }
    )
}

@Composable
fun BoardWriteBody(
    viewModel: BoardWriteViewModel
) {
    var isShow by remember { mutableStateOf(false) }
    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = viewModel::updateImage
    )
    val info = viewModel.info.value

    LazyColumn(
        contentPadding = PaddingValues(
            top = 15.dp,
            start = 20.dp,
            end = 20.dp,
            bottom = 30.dp
        ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            Text(
                text = info.teamName.ifEmpty { "팀 선택" },
                style = textStyle20(),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MyWhite, RoundedCornerShape(3.dp))
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .nonRippleClickable { isShow = true }
            )
        }
        item {
            RestrictedTextField(
                maxLength = 1000,
                value = info.contents,
                minHeight = 300.dp,
                onTextChange = viewModel::updateContents,
                isRegisterButtonVisible = false
            )
        }
        item {
            info.image?.let {
                BoardWriteImageContainer(
                    modifier = Modifier,
                    uri = it,
                    onDelete = {
                        viewModel.updateImage(null)
                    },
                    onChange = {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
            } ?: run {
                AddImageBox(
                    modifier = Modifier,
                    onClick = {
                        singlePhotoPicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                )
            }
        }
    }

    TeamSelectDialog(
        isShow = isShow,
        onDismiss = { isShow = false },
        onItemClick = viewModel::selectTeam
    )
}

@Composable
fun AddImageBox(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxWidth()
            .height(235.dp)
            .background(MyLightBlack, RoundedCornerShape(3.dp))
            .dashedBorder(1.dp, 3.dp, MyWhite)
            .nonRippleClickable(onClick)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "첨부할 이미지를 선택해주세요",
                style = textStyle20B(textAlign = TextAlign.Center),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun BoardWriteImageContainer(
    modifier: Modifier,
    uri: Uri,
    onDelete: () -> Unit,
    onChange: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = uri,
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(3.dp))
                .border(1.dp, MyWhite, RoundedCornerShape(3.dp))
        )
        Row(
            modifier = Modifier
                .padding(top = 10.dp)
                .align(Alignment.End)
        ) {
            Text(
                text = "삭제",
                style = textStyle20B(),
                modifier = Modifier
                    .background(MyRed, RoundedCornerShape(3.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .nonRippleClickable(onDelete)
            )
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = "변경",
                style = textStyle20B(),
                modifier = Modifier
                    .background(MainColor, RoundedCornerShape(3.dp))
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                    .nonRippleClickable(onChange)
            )
        }
    }
}