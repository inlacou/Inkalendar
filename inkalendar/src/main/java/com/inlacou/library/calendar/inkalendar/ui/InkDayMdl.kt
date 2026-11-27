package com.inlacou.library.calendar.inkalendar.ui

import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.business.Day
import java.time.LocalDate

data class InkDayMdl(
    val model: Day = Day(),
    val selectedBackResId: Int? = R.drawable.inkalendar_selected_day_background,
    val specialTextBackColorResId: Int? = R.color.inkalendar_back_disabled_color,
    val textNormalColorResId: Int = R.color.inkalendar_text_normal_color,
    val textSelectedColorResId: Int = R.color.inkalendar_text_selected_color,
    val textSpecialColorResId: Int = R.color.inkalendar_text_special_color,
    val textDisabledColorResId: Int = R.color.inkalendar_text_disabled_color,
    val textDisabledOtherMonthColorResId: Int = R.color.inkalendar_text_disabled_other_month_color,
    val isSelected: Boolean = false,
    val isCurrentMonth: Boolean = false,
    val onClick: ((item: InkDayMdl) -> Unit)? = null
) {
	override fun equals(other: Any?): Boolean {
		if (other is InkDayMdl) {
			return model.date == other.model.date &&
					isSelected == other.isSelected &&
					isCurrentMonth == other.isCurrentMonth
		}
		return super.equals(other)
	}

	override fun hashCode(): Int {
		var result = model.date.hashCode()
		result = 31 * result + isSelected.hashCode()
		result = 31 * result + isCurrentMonth.hashCode()
		return result
	}
}

fun computeDays(position: Int, calendarModel: InkalendarMdl): List<InkDayMdl> {
	val days = mutableListOf<InkDayMdl>()
	val monthDate = calendarModel.today.plusMonths(position.toLong()).withDayOfMonth(1)

	val firstDayOfMonth = monthDate.withDayOfMonth(1)
	val dayOfWeek = firstDayOfMonth.dayOfWeek.value // Monday=1, ..., Sunday=7
	val daysFromPreviousMonth = dayOfWeek - 1

	var startDate = firstDayOfMonth.minusDays(daysFromPreviousMonth.toLong())

	val start = startDate.minusDays(1)
	val end = startDate.plusDays(43)
	val filteredDays = calendarModel.days.filter {
		it.date.isAfter(start) && it.date.isBefore(end)
	}

	fun addDay(date: LocalDate) {
		val dayInl = filteredDays.find { it.date == date }
		val isCurrentMonth = date.month == monthDate.month

		val dayMdl = InkDayMdl(
			model = dayInl ?: Day(date),
			isSelected = calendarModel.selectedDays.any { it == date },
			isCurrentMonth = isCurrentMonth
		)
		days.add(dayMdl)
	}

	for (i in 0 until 42) {
		addDay(startDate)
		startDate = startDate.plusDays(1)
	}

	return days
}