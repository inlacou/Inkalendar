package com.inlacou.library.calendar.calendarviewinl.calendar.views.day

import android.util.Log

class DayViewCtrl(val view: DayView, var model: DayViewMdl) {

	init { //Initialize

	}

	fun onClick() {
		model.onClick?.invoke(model.copy(onClick = null))
	}
}