package com.inlacou.library.calendar.calendarviewinl.calendar.views.day

import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl

data class DayViewMdl @JvmOverloads constructor(
		val model: DayInl = DayInl(),
		val selectedBackResId: Int? = R.drawable.cvinl_selected_background,
		val specialTextBackColorResId: Int? = R.color.cvinl_back_disabled_color,
		val textNormalColorResId: Int = R.color.cvinl_text_normal_color,
		val textSelectedColorResId: Int = R.color.cvinl_text_selected_color,
		val textSpecialColorResId: Int = R.color.cvinl_text_special_color,
		val textDisabledColorResId: Int = R.color.cvinl_text_disabled_color,
		val textDisabledOtherMonthColorResId: Int = R.color.cvinl_text_disabled_other_month_color,
		var onClick: ((item: DayViewMdl) -> Any?)? = null
){
	var isSelected: Boolean= false
	var isCurrentMonth: Boolean = false

	override fun equals(other: Any?): Boolean {
		if(other!=null && other is DayViewMdl){
			return model.calendar == other.model.calendar
		}
		return super.equals(other)
	}
}