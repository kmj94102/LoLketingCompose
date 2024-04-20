package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightGray
import com.example.lolketingcompose.util.textStyle14
import com.example.lolketingcompose.util.textStyle22B
import com.example.network.model.Grade

@Composable
fun PromotionDialog(
    isShow: Boolean,
    grade: Grade,
    onDismiss: () -> Unit
) {
    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        okClick = onDismiss,
        isSingleButton = true,
        contents = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = grade.image,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                
                Text(text = "축하합니다", style = textStyle22B())
                Spacer(modifier = Modifier.height(10.dp))
                
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = MainColor)) {
                            append(grade.koreanName)
                        }
                        append(" 랭크로 승급하였습니다.")
                    },
                    style = textStyle14(color = MyLightGray, textAlign = TextAlign.Center),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}