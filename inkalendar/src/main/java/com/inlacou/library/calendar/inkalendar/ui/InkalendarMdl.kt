package com.inlacou.library.calendar.inkalendar.ui

import com.inlacou.library.calendar.inkalendar.business.Day
import java.time.LocalDate

data class InkalendarMdl(
	/**
	 * Current day for the calendar. Usually it will just be the real today.
	 */
	val today: LocalDate = LocalDate.now(),
	val days: List<Day> = listOf(),
	val selectedDays: List<LocalDate> = listOf(),
	val minimumDate: LocalDate? = null,
	val maximumDate: LocalDate? = null,
	val mode: Mode = Mode.MULTIPLE_SELECTION,
) {
	enum class Mode {
		SINGLE_SELECTION, MULTIPLE_SELECTION //TODO: NO_SELECTION, RANGE_SELECTION
	}
}
