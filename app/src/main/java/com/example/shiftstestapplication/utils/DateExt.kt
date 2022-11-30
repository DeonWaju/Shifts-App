package com.example.shiftstestapplication.utils

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
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

fun String.toSlashedDateNoMilli(): String {
    val date = this.toDate()
    return SimpleDateFormat(slashPattern, Locale.getDefault()).format(date)
}

fun String.toMonthDateNoMilli(): String {
    val date = this.toDate()
    return SimpleDateFormat(monthPattern, Locale.getDefault()).format(date)
}

fun String.toFullMonthDateNoMilli(): String {
    val date = this.toDate()
    return SimpleDateFormat(monthFullPattern, Locale.getDefault()).format(date)
}

fun String.toDBStartDate(): String {
    val date = this.toDate()
    return SimpleDateFormat("yyyy'-'MM'-'dd'T'00':'00", Locale.getDefault()).format(date)
}

fun String.toDBEndDate(): String {
    val date = this.toDate()
    return SimpleDateFormat("yyyy'-'MM'-'dd'T'23':'59", Locale.getDefault()).format(date)
}

fun String.toTimeInHours(): String {
    val date = this.toMilliDate()
    val hrly = SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
    return String.format("%s Hrs", hrly)
}

fun String.toSlashedDate(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat(slashPattern, Locale.getDefault()).format(date)
}

fun String.toFriendlyDateWithTime(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat("EEE, MMM dd, yyyy hh:mm a", Locale.getDefault()).format(date)
}

fun String.toSlashedFriendlyDateWithTime(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat("EEE dd/MMM, yyyy hh:mm a", Locale.getDefault()).format(date)
}

fun Long.toTractionDateFormat(): String {
    val pattern = "yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSS"
    val date = Date(this)
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(date)
}

fun Long.toTractionDateFormatWithZ(): String {
    val pattern = "yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSS'Z'"
    val date = Date(this)
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(date)
}

fun String.toSalesDetailFormat(): String {
    val date = this.toMilliDate()
    return SimpleDateFormat("hh:mm a dd/MM/yyyy", Locale.getDefault()).format(date)
}

fun Long.toHyphenDateString(): String {
    val pattern = "yyyy-MM-dd"
    val date = Date(this)
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(date)
}

fun Long.toSimpleDateString(): String {
    val pattern = "MMM dd, yyyy"
    val date = Date(this)
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.format(date)
}

fun String.toSimpleDateString(): String {
    val pattern = "MMM dd, yyyy"
    val date = this.toDate()
    return SimpleDateFormat(pattern, Locale.getDefault()).format(date)
}

fun Date.toTimeAgo(): String {
    val calendar = Calendar.getInstance()
    calendar.time = this

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val currentCalendar = Calendar.getInstance()

    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentMonth = currentCalendar.get(Calendar.MONTH)
    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
    val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
    val currentMinute = currentCalendar.get(Calendar.MINUTE)

    return if (year < currentYear) {
        val interval = currentYear - year
        if (interval == 1) "$interval year ago" else "$interval years ago"
    } else if (month < currentMonth) {
        val interval = currentMonth - month
        if (interval == 1) "$interval month ago" else "$interval months ago"
    } else if (day < currentDay) {
        val interval = currentDay - day
        if (interval == 1) "$interval day ago" else "$interval days ago"
    } else if (hour < currentHour) {
        val interval = currentHour - hour
        if (interval == 1) "$interval hour ago" else "$interval hours ago"
    } else if (minute < currentMinute) {
        val interval = currentMinute - minute
        if (interval == 1) "$interval minute ago" else "$interval minutes ago"
    } else {
        "a moment ago"
    }
}

fun String.elapsedDays(): Long {
    val calendar = Calendar.getInstance()
    calendar.time = this.toDate()

    return TimeUnit.MILLISECONDS.toDays(Date().time - calendar.timeInMillis)
}

fun String.toMilliDateZ(): Date {
    return SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss.SSS ", Locale.getDefault()).parse(this)
}

fun String.simpleStringToDate(): Date {
    return SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).parse(this)
}

fun Long.toDayMonthInFull(): String {
    return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(Date(this))
}

fun Long.toDDMMMYYYY(): String {
    return SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(this))
}


fun Long.toDayMonthYear(): String {
    return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date(this))
}

fun String.toDayMonthInFull(): String {
    return SimpleDateFormat("dd MMMM", Locale.getDefault()).format(this.toDate())
}

fun String.toDayMonthShort(): String {
    return SimpleDateFormat("dd MMM", Locale.getDefault()).format(this.toDate())
}

fun Long.toDayMonthYearInFull(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(this))
}

fun String.toMonthDayYearInFull(): String { //2020-11-20T00:50:09.14+01:00
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this.toDate())
}

fun Long.toDottedDDMMYYYY(): String {
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(this))
}

fun String.dayMonthToFriendlyDate(): String {
    return try {
        val date = SimpleDateFormat("dd'-'MM", Locale.getDefault()).parse(this)
        date.time.toDayMonthInFull()
    } catch (ex: Exception) {
        "N/A"
    }
}

fun String.dayMonthYearToDate(): String {
    return try {
        val date = SimpleDateFormat(commonDateFormat, Locale.getDefault()).parse(this)
        date.time.toDayMonthYear()
    } catch (ex: Exception) {
        "N/A"
    }
}

fun Long.toInvoiceCreateDate(): String {
    val date = Date(this) // "yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'"
    return SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'", Locale.getDefault()).format(date)
}

fun Date.toDayMonthString(): String {
    return SimpleDateFormat("dd'-'MM", Locale.getDefault()).format(this)
}


fun String.timeAgo(): String {
    return DateUtils.getRelativeTimeSpanString(
        this.toMilliDate().time,
        Calendar.getInstance().timeInMillis,
        DateUtils.SECOND_IN_MILLIS,
        DateUtils.FORMAT_ABBREV_ALL
    ).toString()
}

fun String.toNewDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun String.toNewDateStyle(dateFormat: String = "yyyy-MM-dd"): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}