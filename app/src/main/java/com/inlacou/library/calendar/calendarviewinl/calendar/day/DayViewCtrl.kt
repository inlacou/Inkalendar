package com.inlacou.library.calendar.calendarviewinl.calendar.day

import android.util.Log

class DayViewCtrl(val view: DayView, var model: DayViewMdl) {

	init { //Initialize

	}

	fun onClick() {
		Log.d("DEBUG.DayViewCtrl", "onClick: $model")
		model.onClick?.invoke(model.copy(onClick = null))
	}
}