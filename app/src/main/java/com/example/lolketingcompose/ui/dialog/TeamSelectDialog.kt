package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lolketingcompose.ui.custom.CommonButton
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.util.nonRippleClickable
import com.example.lolketingcompose.util.textStyle22B
import com.example.network.model.Team

@Composable
fun TeamSelectDialog(
    isShow: Boolean,
    isAllVisible: Boolean = false,
    onDismiss: () -> Unit,
    onItemClick: (Team) -> Unit
) {
    CommonDialogContainer(
        isShow = isShow,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.clip(RoundedCornerShape(10.dp))
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "팀 선택",
            style = textStyle22B(textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Divider(color = MyLightGray, thickness = 0.5.dp)

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content = {
                items(Team.getTeamList()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(0.5.dp, MyLightGray)
                            .padding(5.dp)
                            .nonRippleClickable {
                                onItemClick(it)
                                onDismiss()
                            }
                    ) {
                        AsyncImage(
                            model = it.image,
                            contentDescription = null,
                            modifier = Modifier.size(119.dp, 85.dp)
                        )
                    }
                }
            }
        )

        if (isAllVisible) {
            CommonButton(
                text = "전체보기",
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .fillMaxWidth()
                    .nonRippleClickable {
                        onItemClick(Team.ALL)
                        onDismiss()
                    }
            )
        }
    }
}