package com.inlacou.library.calendar.inkalendar.ui

import com.inlacou.library.calendar.inkalendar.business.Day
import java.time.LocalDate

data class InkalendarMdl(
	/**
		 * Current day for the calendar. Usually it will just be the real today.
		 */
		var today: LocalDate = LocalDate.now(),
	val days: List<Day> = listOf(),
	val selectedDays: MutableList<LocalDate> = mutableListOf(),
	val minimumDate: LocalDate? = null,
	val maximumDate: LocalDate? = null,
	val mode: Mode = Mode.MULTIPLE_SELECTION,
	val singleDaySelection: ((selectedDay: LocalDate) -> Any?)? = null,
	val multiDaySelection: ((selectedDays: MutableList<LocalDate>) -> Any?)? = null,
	/**
		 * New month and year here
		 */
		val onForward: ((position: LocalDate) -> Any?)? = null,
	/**
		 * New month and year here
		 */
		val onBackward: ((position: LocalDate) -> Any?)? = null,
	/**
		 * New page displayed days from-to
		 */
		val onPageLoad: ((fromTo: Pair<LocalDate, LocalDate>) -> Any?)? = null
){
	var currentPage: Int = FIRST_VISIBLE_PAGE
	internal val anchor = today
	companion object {
		const val FIRST_VISIBLE_PAGE = 2401 / 2 // Replaced CalendarPagerAdapter.CALENDAR_SIZE with constant for simplicity
	}
	enum class Mode{
		SINGLE_SELECTION, MULTIPLE_SELECTION //TODO, NO_SELECTION, RANGE_SELECTION
	}
}