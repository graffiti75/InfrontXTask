package br.android.cericatto.infrontxtask.util

import java.lang.NumberFormatException

/**
 * Input: 2019-02-02T15:00:00.000Z
 * Output: Feb 2, 2019 at 15:00
 */
fun String.formatDate(): String {
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
    return "${a[1].formatMonth()} ${b[0].dayTwoDigits()}, ${a[0]} at ${d[0]}:${d[1]}"
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
    try {
        val number = this.toInt()
        if (number < 10) return this.toInt().toString()
    } catch (e: NumberFormatException) {
        println(e.message)
    }
    return ""
}