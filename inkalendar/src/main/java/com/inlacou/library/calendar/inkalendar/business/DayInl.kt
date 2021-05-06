package com.inlacou.library.calendar.inkalendar.business

import com.inlacou.library.calendar.inkalendar.toMidnight
import java.util.*

data class DayInl(
		val calendar: Calendar = Calendar.getInstance().toMidnight()!!,
		val isEnabled: Boolean = true,
		val isSpecial: Boolean = false,
		val iconResId: Int? = null,
		val colorResId: Int? = null,
		val colorHex: String? = null,
)