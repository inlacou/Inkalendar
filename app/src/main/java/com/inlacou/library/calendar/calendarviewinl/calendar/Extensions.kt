package com.inlacou.library.calendar.calendarviewinl.calendar

import android.content.Context
import com.inlacou.library.calendar.calendarviewinl.R
import java.util.*

/**
 * This method sets an hour in the calendar object to 00:00:00:00
 *
 * @param calendar Calendar object which hour should be set to 00:00:00:00
 */
fun Calendar?.toMidnight(): Calendar? {
	this?.let {
		set(Calendar.HOUR_OF_DAY, 0)
		set(Calendar.MINUTE, 0)
		set(Calendar.SECOND, 0)
		set(Calendar.MILLISECOND, 0)
	}
	return this
}

/**
 * This method compares calendars using month and year
 *
 * @param this  First calendar object to compare
 * @param calendar Second calendar object to compare
 * @return Boolean value if second calendar is before the first one
 */
fun Calendar?.isMonthBefore(calendar: Calendar): Boolean {
	if (this == null) {
		return false
	}

	val firstDay = this.clone() as Calendar
	firstDay.toMidnight()
	firstDay.set(Calendar.DAY_OF_MONTH, 1)
	val secondDay = calendar.clone() as Calendar
	secondDay.toMidnight()
	secondDay.set(Calendar.DAY_OF_MONTH, 1)

	return secondDay.before(firstDay)
}


/**
 * This method compares calendars using month and year
 *
 * @param this  First calendar object to compare
 * @param calendar Second calendar object to compare
 * @return Boolean value if second calendar is after the first one
 */
fun Calendar?.isMonthAfter(calendar: Calendar): Boolean {
	if (this == null) {
		return false
	}

	val firstDay = this.clone() as Calendar
	firstDay.toMidnight()
	firstDay.set(Calendar.DAY_OF_MONTH, 1)
	val secondDay = calendar.clone() as Calendar
	secondDay.toMidnight()
	secondDay.set(Calendar.DAY_OF_MONTH, 1)

	return secondDay.after(firstDay)
}

fun Long?.toDay(context: Context): String = context.getString(R.string.day, this)

fun Long?.toMonthYear(context: Context, monthAsText: Boolean = true): String =
	if(monthAsText) context.getString(R.string.month_as_text_year, this)
	else context.getString(R.string.month_year, this)

