package com.inlacou.library.calendar.inkalendar.views.day

class DayViewCtrl(val view: DayView, var model: DayViewMdl) {
	fun onClick() {
		model.onClick?.invoke(model.copy(onClick = null))
	}
}