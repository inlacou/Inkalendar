package com.inlacou.library.calendar.inkalendar

import android.content.Context
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun LocalDate.sameYearAndMonth(other: LocalDate): Boolean = 
    year == other.year && month == other.month

fun LocalDate?.isMonthBefore(other: LocalDate): Boolean {
    if (this == null) return false
    return this.withDayOfMonth(1).isBefore(other.withDayOfMonth(1))
}

fun LocalDate?.isMonthAfter(other: LocalDate): Boolean {
    if (this == null) return false
    return this.withDayOfMonth(1).isAfter(other.withDayOfMonth(1))
}

fun LocalDate.toDay(context: Context): String {
    val formatter = DateTimeFormatter.ofPattern(context.getString(R.string.inkalendar_day_pattern))
    return this.format(formatter)
}

fun LocalDate.toMonthYear(context: Context, monthAsText: Boolean = true): String {
    val pattern = if (monthAsText) {
        context.getString(R.string.inkalendar_month_as_text_year_pattern)
    } else {
        context.getString(R.string.inkalendar_month_year_pattern)
    }
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return this.format(formatter)
}
