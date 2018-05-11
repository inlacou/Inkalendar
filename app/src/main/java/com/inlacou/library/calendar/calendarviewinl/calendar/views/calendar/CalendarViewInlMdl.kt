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
		val mode: Mode = Mode.MULTIPLE_SELECTION,
		/**
		 * New month and year here
		 */
		val onForward: ((position: Calendar) -> Any?)? = null,
		/**
		 * New month and year here
		 */
		val onBackward: ((position: Calendar) -> Any?)? = null,
		/**
		 * New page displayed days from-to
		 */
		val onPageLoad: ((fromTo: Pair<Calendar, Calendar>) -> Any?)? = null
){
	var currentPage: Int = FIRST_VISIBLE_PAGE
	companion object {
		val FIRST_VISIBLE_PAGE = CalendarPagerAdapter.CALENDAR_SIZE / 2
	}

	enum class Mode{
		SINGLE_SELECTION, MULTIPLE_SELECTION //TODO, NO_SELECTION, RANGE_SELECTION
	}
}