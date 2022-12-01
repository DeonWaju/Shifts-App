package com.example.shiftstestapplication.utils

import android.os.Build
import android.text.format.DateUtils
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern


const val commonDateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ss"
const val commonDateFormatNoSSS = "yyyy'-'MM'-'dd'T'HH':'mm':'ss"
const val slashPattern = "dd/MM/yyyy"
const val monthFullPattern = "dd MMMM yyyy"
const val monthPattern = "dd MMM yyyy"

fun String.toDashboardDate(): String {
    val isYMD =
        Pattern.compile("[12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])").matcher(this).matches()
    val isYM = Pattern.compile("[12]\\d{3}-(0[1-9]|1[0-2])").matcher(this).matches()

    return when {
        isYMD -> {
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(this)
            SimpleDateFormat("MMM dd", Locale.getDefault()).format(date)
        }
        isYM -> {
            val date = SimpleDateFormat("yyyy-MM", Locale.getDefault()).parse(this)
            SimpleDateFormat("MMM''yy", Locale.getDefault()).format(date)
        }
        else -> {
            val date = SimpleDateFormat("yyyy", Locale.getDefault()).parse(this)
            SimpleDateFormat("yyyy", Locale.getDefault()).format(date)
        }
    }
}


private fun getDayOfMonthSuffix(n: Int): String {
    if (n in 11..13) {
        return "th"
    }
    return when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}

fun String.toDateNoTime(): Date {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(this)
}

fun String.toHyphenStringtoDate(): Date {
    return SimpleDateFormat("yyy-MM-dd", Locale.getDefault()).parse(this)
}

fun String.toDate(): Date {
    return try {
        SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss", Locale.getDefault()).parse(this)
    } catch (ex: Exception) {
        SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss",
            Locale.getDefault()).parse("1970-01-01T00:00:00")
    }
}

fun String.toCurrentDate(): String {
    val date = SimpleDateFormat("dd' 'MMM', 'yyyy",
        Locale.getDefault()).format(Date(System.currentTimeMillis()))
    return date
}

fun String.toMilliDate(): Date {
    return try {
        val sf = SimpleDateFormat(commonDateFormat,
            Locale.getDefault())
        sf.timeZone = TimeZone.getTimeZone("UTC")
        sf.parse(this) //"2019-11-12T17:09:59+01:00"
    } catch (ex: Exception) {
        SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSS'Z'",
            Locale.getDefault()).parse("1970-01-01T00:00:00.000Z")
    }
}

fun String.to24HRTimeString(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
}

fun String.toSecondOnlyWithZ(): String {
    val date = this.toMilliDate() // "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'"
    return SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'", Locale.getDefault()).format(date)
}

fun String.to12HRTimeString(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat("HH:mm a", Locale.getDefault()).format(date)
}

fun String.to12HRTimeStringFrom24(): String {
    try {
        val sdf = SimpleDateFormat("H:mm")
        val dateObj = sdf.parse(this)
        return SimpleDateFormat("K:mm a").format(dateObj)
    } catch (e: ParseException) {
        e.printStackTrace()
        return ""
    }
}

fun String.toFriendlyDateString(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date)
}

fun String.toDayMonthYearDDMMMYYYY(): String {
    val date = this.toMilliDate()
    val sf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    sf.timeZone = TimeZone.getDefault()
    return sf.format(date)
}

fun String.toInvoiceDueDateDDMMMYYYY(): String {
    val date = this.toDate()
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)
}

fun String.toFriendlyDate(): Date {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(this)
}

fun String.toDayMonthYear(): String {
    val date = this.toDateNoTime()
    val dayFormate = SimpleDateFormat("d", Locale.getDefault())
    val day = dayFormate.format(date)
    return String.format(
        "$day${getDayOfMonthSuffix(day.toInt())} %s",
        SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(date)
    )
}

fun Calendar.sameDay(other: Calendar): Boolean {
    return (this.get(Calendar.DAY_OF_MONTH) == other.get(
        Calendar.DAY_OF_MONTH
    )) && (this.get(Calendar.MONTH) == other.get(
        Calendar.MONTH
    )) && (this.get(Calendar.YEAR) == other.get(Calendar.YEAR))
}

// convert local date to 2018-04-20 9:00:00 -08:00
@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return this.atStartOfDay().format(formatter)
}

// convert dateTime to “Mon, April 12 9-2 PM”
@RequiresApi(Build.VERSION_CODES.O)
fun String.toDateStyle(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val date = LocalDateTime.parse(this, formatter)
    val day = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val month = date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dayOfMonth = date.dayOfMonth
    val startHour = date.hour
    val startMinute = date.minute
    val endHour = date.hour + 5
    val endMinute = date.minute
    val dayShort = day.substring(0, 3)
    return "$dayShort, $month $dayOfMonth ${startMinute - endHour} ${if (startHour > 12) "PM" else "AM"}"
}