package com.inlacou.library.calendar.calendarviewinl.calendar.business

import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import java.util.*

data class DayInl(
		val calendar: Calendar = Calendar.getInstance().toMidnight()!!,
		val isEnabled: Boolean = true,
		val isSpecial: Boolean = false,
		val iconResId: Int? = null
)