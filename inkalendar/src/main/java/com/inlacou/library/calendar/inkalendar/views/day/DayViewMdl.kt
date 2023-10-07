package com.inlacou.library.calendar.inkalendar.views.day

import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.business.DayInl

data class DayViewMdl @JvmOverloads constructor(
	val model: DayInl = DayInl(),
	val selectedBackResId: Int? = R.drawable.inkalendar_selected_day_background,
	val specialTextBackColorResId: Int? = R.color.inkalendar_back_disabled_color,
	val textNormalColorResId: Int = R.color.inkalendar_text_normal_color,
	val textSelectedColorResId: Int = R.color.inkalendar_text_selected_color,
	val textSpecialColorResId: Int = R.color.inkalendar_text_special_color,
	val textDisabledColorResId: Int = R.color.inkalendar_text_disabled_color,
	val textDisabledOtherMonthColorResId: Int = R.color.inkalendar_text_disabled_other_month_color,
	var onClick: ((item: DayViewMdl) -> Any?)? = null
) {
	var isSelected: Boolean= false
	var isCurrentMonth: Boolean = false

	override fun equals(other: Any?): Boolean {
		if(other!=null && other is DayViewMdl){
			return model.calendar == other.model.calendar
		}
		return super.equals(other)
	}
}