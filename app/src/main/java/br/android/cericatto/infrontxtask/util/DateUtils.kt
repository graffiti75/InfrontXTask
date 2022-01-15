package br.android.cericatto.infrontxtask.util

import android.content.Context
import br.android.cericatto.infrontxtask.R
import java.text.SimpleDateFormat
import java.util.*

const val SUFFIX = ":00"

/**
 * Input: 2019-02-02T15:00:00.000Z
 * Output: Feb 2, 2019 at 15:00
 */
fun String.formatDateToAdapter(): String {
    val a = this.split("-")
    // 2019
    // 02
    // 02T15:00:00.000Z
    val b = a[2].split("T")
    // 02
    // 15:00:00.000Z
    val c = b[1].split(".")
    // 15:00:00
    // 000Z
    val d = c[0].split(":")
    // 15
    // 00
    // 00
    var monthDayString = b[0]
    var monthDay = b[0].toInt()
    if (monthDay < 10) monthDayString = b[0].toInt().toString()
    return "${a[1].formatMonth()} $monthDayString, ${a[0]} at ${d[0]}:${d[1]}"
}

fun String.weekday(context: Context): String {
    val isoFormat = SimpleDateFormat(context.getString(R.string.iso_8601_format))
    val dateInIso = isoFormat.parse(this)
    val weekdayFormat = SimpleDateFormat(context.getString(R.string.weekday_format))
    return weekdayFormat.format(dateInIso).getStringWithoutLastChar()
}

fun String.formatDateToAdapter(context: Context): String {
    val isoFormat = SimpleDateFormat(context.getString(R.string.iso_8601_format))
    val dateInIso = isoFormat.parse(this)
    val infrontxFormat = SimpleDateFormat(context.getString(R.string.infrontx_date_item_adapter_format))
    val rawDate = infrontxFormat.format(dateInIso)

    // Get data for the current timezone.
    val a = dateInIso.toString().split(" ")
    val gmtDelta = a[4].substring(4, 9) + SUFFIX

    val b = rawDate.toString().split(" ")
    val hour = b[4] + SUFFIX

    val subtraction = hour.subtractDates(gmtDelta)
    val c = rawDate.split(" ")
    return "${c[0].getStringWithoutLastChar().capitalizeString()} ${c[1]} ${c[2]} at $subtraction"
}

fun String.dayOfMonth(): String {
    val a = this.split("-")
    val b = a[2].split("T")
    return b[0].dayTwoDigits()
}

fun String.formatMonth(): String {
    return when(this) {
        "01" -> "Jan"
        "02" -> "Feb"
        "03" -> "Mar"
        "04" -> "Apr"
        "05" -> "May"
        "06" -> "Jun"
        "07" -> "Jul"
        "08" -> "Aug"
        "09" -> "Sep"
        "10" -> "Oct"
        "11" -> "Nov"
        "12" -> "Dec"
        else -> ""
    }
}

fun String.dayTwoDigits(): String {
    val number = this.toInt()
    if (number < 10) return "0$number"
    return this
}

fun String.subtractDates(last: String): String {
    val a = this.split(":")
    val aHour = a[0].toInt()
    val aMinute = a[1].toInt()
    val aSecond = a[2].toInt()
    val aMillis = aHour * 3600 + aMinute * 60 + aSecond

    val b = last.split(":")
    val bHour = b[0].toInt()
    val bMinute = b[1].toInt()
    val bSecond = b[2].toInt()
    val bMillis = bHour * 3600 + bMinute * 60 + bSecond

    val subtraction = aMillis - bMillis
    val cHour = subtraction / 3600
    val cMinute = subtraction % 60

    return "${cHour.toString().dayTwoDigits()}:${cMinute.toString().dayTwoDigits()}"
}

fun String.capitalizeString() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.getStringWithoutLastChar() = this.substring(0, this.length - 1)