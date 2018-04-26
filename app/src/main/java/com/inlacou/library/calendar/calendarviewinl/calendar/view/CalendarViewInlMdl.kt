package com.inlacou.library.calendar.calendarviewinl.calendar.view

import com.inlacou.library.calendar.calendarviewinl.calendar.adapters.CalendarPagerAdapter
import java.util.*

data class CalendarViewInlMdl(
		val today: Calendar = Calendar.getInstance(),
		val current: Calendar = Calendar.getInstance(), //It should be a var, I'm waiting for the IDE to tell me
		var currentPage: Int = FIRST_VISIBLE_PAGE, //Redundant?
		val minimumDate: Calendar? = null,
		val maximumDate: Calendar? = null,
		val onForward: (() -> Unit?)? = null,
		val onBackward: (() -> Unit?)? = null
){
	companion object {
		val FIRST_VISIBLE_PAGE = CalendarPagerAdapter.CALENDAR_SIZE / 2
	}
}