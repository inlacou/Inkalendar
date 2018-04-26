package com.inlacou.library.calendar.calendarviewinl.calendar.day

import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import java.util.*

data class DayViewMdl @JvmOverloads constructor(
		val day: Calendar = Calendar.getInstance().toMidnight()!!,
		val isSelected: Boolean = false,
		val isEnabled: Boolean = true,
		val isSpecial: Boolean = false,
		val selectedBackColorResId: Int? = null,
		val specialTextBackColorResId: Int? = null,
		val textNormalColorResId: Int = R.color.cvinl_text_normal_color,
		val textSelectedColorResId: Int = R.color.cvinl_text_selected_color,
		val textSpecialColorResId: Int = R.color.cvinl_text_special_color,
		val textDisabledColorResId: Int = R.color.cvinl_text_disabled_color,
		val disabledTextColorResId: Int? = null,
		val iconResId: Int? = null,
		val onClick: ((item: DayViewMdl) -> Unit?)? = null
){
	var isCurrentMonth: Boolean = false

	override fun equals(other: Any?): Boolean {
		if(other!=null && other is DayViewMdl){
			return day == other.day
		}
		return super.equals(other)
	}
}