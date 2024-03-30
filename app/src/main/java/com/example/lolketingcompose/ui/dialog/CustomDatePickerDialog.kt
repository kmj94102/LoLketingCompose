package com.example.lolketingcompose.ui.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.lolketingcompose.ui.theme.MainColor
import com.example.lolketingcompose.ui.theme.MyLightBlack
import com.example.lolketingcompose.ui.theme.MyWhite
import com.example.lolketingcompose.ui.theme.MyYellow
import com.example.lolketingcompose.util.formatDateFromTimestamp
import com.example.lolketingcompose.util.textStyle16
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    isShow: Boolean,
    selectedDate: String,
    onDismiss: () -> Unit,
    okClick: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        initialSelectedDateMillis = selectedDate.let {
            val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            formatter.parse(it)?.time ?: System.currentTimeMillis()
        }
    )

    CommonConfirmDialog(
        isShow = isShow,
        onDismiss = onDismiss,
        okClick = {
            datePickerState.selectedDateMillis?.let {
                okClick(formatDateFromTimestamp(it))
                onDismiss()
            }
        },
        title = {
            Text(
                text = "날짜 선택",
                style = textStyle16(textAlign = TextAlign.Center),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.padding(horizontal = 10.dp),
        contents = {
            DatePicker(
                state = datePickerState,
                title = null,
                headline = null,
                colors = DatePickerDefaults.colors(
                    weekdayContentColor = MyYellow,
                    containerColor = MyLightBlack,
                    selectedDayContainerColor = MainColor,
                    todayDateBorderColor = MainColor,
                    todayContentColor = MainColor,
                    selectedYearContainerColor = MainColor,
                    currentYearContentColor = MainColor,
                    selectedDayContentColor = MyWhite,
                    yearContentColor = MyWhite,
                    selectedYearContentColor = MyWhite,
                    dayContentColor = MyWhite,
                    subheadContentColor = MyWhite
                ),
                showModeToggle = false,
            )
        },
    )
}