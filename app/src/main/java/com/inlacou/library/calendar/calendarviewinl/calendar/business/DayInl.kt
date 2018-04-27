package com.inlacou.library.calendar.calendarviewinl.calendar.business

import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import java.util.*

data class DayInl(
		val calendar: Calendar = Calendar.getInstance().toMidnight()!!,
		val isSelected: Boolean = false,
		val isEnabled: Boolean = true,
		val isSpecial: Boolean = false
)