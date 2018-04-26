package com.inlacou.library.calendar.calendarviewinl.calendar.day

import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import java.util.*

data class DayViewMdl @JvmOverloads constructor(
		val day: Date = Calendar.getInstance().toMidnight()!!.time,
		val selected: Boolean? = null,
		val iconResId: Int? = null,
		val onClick: ((item: DayViewMdl) -> Unit?)? = null
){
	override fun equals(other: Any?): Boolean {
		if(other!=null && other is DayViewMdl){
			return day == other.day
		}
		return super.equals(other)
	}
}