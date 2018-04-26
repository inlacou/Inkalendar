package com.inlacou.library.calendar.calendarviewinl.calendar.view

import com.inlacou.library.calendar.calendarviewinl.calendar.isMonthAfter
import com.inlacou.library.calendar.calendarviewinl.calendar.isMonthBefore
import java.util.*


class CalendarViewInlCtrl(val view: CalendarViewInl, var model: CalendarViewInlMdl) {

	init { //Initialize

	}

	fun onClick() {
		//TODO model.onClick?.invoke(model.copy(onClick = null))
	}

	fun isScrollingLimited(calendar: Calendar, position: Int): Boolean {
		if (model.minimumDate.isMonthBefore(calendar)) {
			view.moveToNext(position)
			return true
		}

		if (model.maximumDate.isMonthAfter(calendar)) {
			view.moveToPrevious(position)
			return true
		}

		return false
	}

	fun onPageSelected(position: Int) {
		val calendar = model.current.clone() as Calendar
		calendar.add(Calendar.MONTH, position)

		if (!isScrollingLimited(calendar, position)) {
			view.setHeaderName(calendar)
			callOnPageChangeListeners(position)
		}
	}

	// This method calls page change listeners after swipe calendar or click arrow buttons
	private fun callOnPageChangeListeners(position: Int) {
		if (position > model.currentPage) {
			model.onForward?.invoke()
		}

		if (position < model.currentPage) {
			model.onBackward?.invoke()
		}

		model.currentPage = position
	}
}