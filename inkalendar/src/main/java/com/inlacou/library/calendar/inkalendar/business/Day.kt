package com.inlacou.library.calendar.inkalendar.business

import java.time.LocalDate

data class Day(
		val date: LocalDate = LocalDate.now(),
		val isEnabled: Boolean = true,
		val isSpecial: Boolean = false,
		val iconResId: Int? = null,
		val colorResId: Int? = null,
		val colorHex: String? = null,
)