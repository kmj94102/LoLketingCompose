package com.example.lolketingcompose.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatNumber(number: Int): String {
    return String.format("%,d", number)
}

fun formatDateFromTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}

fun getToday(): String {
    val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
    val currentDate = Date(System.currentTimeMillis())
    return sdf.format(currentDate)
}