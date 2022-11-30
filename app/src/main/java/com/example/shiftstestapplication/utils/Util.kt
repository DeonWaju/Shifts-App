package com.example.shiftstestapplication.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Gideon Olarewaju on 30/11/2022.
 */

@SuppressLint("NewApi")
private fun convertToCustomFormat(dateStr: String?): String {
    val utc = TimeZone.getTimeZone("UTC")
    val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy")
    val destFormat = SimpleDateFormat("dd-MMM-YYYY HH:mm aa")
    sourceFormat.timeZone = utc
    val convertedDate = sourceFormat.parse(dateStr)
    return destFormat.format(convertedDate)
}