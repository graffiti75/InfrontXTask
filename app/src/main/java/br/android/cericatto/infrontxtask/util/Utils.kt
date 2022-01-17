package br.android.cericatto.infrontxtask.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import br.android.cericatto.infrontxtask.R
import br.android.cericatto.infrontxtask.adapter.FixturesRecyclerViewItem
import br.android.cericatto.infrontxtask.adapter.ResultsRecyclerViewItem
import br.android.cericatto.infrontxtask.data.common.CompetitionStage
import br.android.cericatto.infrontxtask.data.common.HomeTeam
import br.android.cericatto.infrontxtask.data.common.Venue
import br.android.cericatto.infrontxtask.data.fixture.FixtureItem
import br.android.cericatto.infrontxtask.data.result.AggregateScore
import br.android.cericatto.infrontxtask.data.result.PenaltyScore
import br.android.cericatto.infrontxtask.data.result.ResultItem
import java.text.SimpleDateFormat
import java.util.*

const val SUFFIX = ":00"

/**
 * =============== Dates ===============
 */

fun String.weekday(context: Context): String {
    val isoFormat = SimpleDateFormat(context.getString(R.string.iso_8601_format))
    val dateInIso = isoFormat.parse(this)
    val weekdayFormat = SimpleDateFormat(context.getString(R.string.weekday_format))
    return weekdayFormat.format(dateInIso).getStringWithoutLastChar()
}

/*
 Input format: 2019-02-02T15:00:00.000Z
 Output format: Feb 2, 2019 at 15:00
 */
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

/*
 Input format: 2019-02-02T15:00:00.000Z
 */
fun String.filterDate(): String {
    val split = this.split("-")
    return "${split[0]}${split[1]}"
}

/*
 Input format: 2019-02
 Output format: February 2019
 */
fun String.formattedMonthYear(context: Context): String {
    val inputYearMonthFormat = SimpleDateFormat(context.getString(R.string.input_year_month_format))
    val outputYearMonthFormat = SimpleDateFormat(context.getString(R.string.output_year_month_format))
    val date = inputYearMonthFormat.parse(this)
    return outputYearMonthFormat.format(date).capitalizeString()
}

/**
 * =============== Strings ===============
 */

fun String.dayTwoDigits(): String {
    val number = this.toInt()
    if (number < 10) return "0$number"
    return this
}

fun String.capitalizeString() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

fun String.getStringWithoutLastChar() = this.substring(0, this.length - 1)

/**
 * =============== RecyclerView ItemType ===============
 */

fun FixtureItem.toFixturesRecyclerViewItem(): FixturesRecyclerViewItem.Fixture {
    return FixturesRecyclerViewItem.Fixture(
        this.awayTeam,
        this.competitionStage,
        this.date,
        this.homeTeam,
        this.id,
        this.state,
        this.venue
    )
}

fun ResultItem.toResultsRecyclerViewItem(): ResultsRecyclerViewItem.Result {
    return ResultsRecyclerViewItem.Result(
        this.awayTeam,
        this.competitionStage,
        this.date,
        this.homeTeam,
        this.id,
        this.score,
        this.venue
    )
}

/**
 * =============== Connectivity ===============
 */

fun Context.isNetworkConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (cm != null) {
        if (Build.VERSION.SDK_INT < 23) {
            val ni = cm.activeNetworkInfo
            if (ni != null) {
                return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            }
        } else {
            val n = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }
    }
    return false
}