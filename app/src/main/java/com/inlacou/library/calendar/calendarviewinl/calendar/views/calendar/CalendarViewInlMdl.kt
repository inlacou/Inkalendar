package com.inlacou.library.calendar.calendarviewinl.calendar.views.calendar

import com.inlacou.library.calendar.calendarviewinl.calendar.adapters.CalendarPagerAdapter
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import java.util.*

data class CalendarViewInlMdl(
		/**
		 * Current day for the calendar. Usually it will just be the real today.
		 */
		val today: Calendar = Calendar.getInstance().toMidnight()!!,
		val days: MutableList<DayInl> = mutableListOf(),
		val selectedDays: MutableList<Calendar> = mutableListOf(),
		val minimumDate: Calendar? = null,
		val maximumDate: Calendar? = null,
		val onForward: ((position: Int) -> Unit?)? = null,
		val onBackward: ((position: Int) -> Unit?)? = null
){
	var currentPage: Int = FIRST_VISIBLE_PAGE
	companion object {
		val FIRST_VISIBLE_PAGE = CalendarPagerAdapter.CALENDAR_SIZE / 2
	}
}