package com.inlacou.library.calendar.calendarviewinl.calendar.day

import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import java.util.*

data class DayViewMdl @JvmOverloads constructor(
		val model: DayInl = DayInl(),
		val selectedBackColorResId: Int? = null,
		val specialTextBackColorResId: Int? = null,
		val textNormalColorResId: Int = R.color.cvinl_text_normal_color,
		val textSelectedColorResId: Int = R.color.cvinl_text_selected_color,
		val textSpecialColorResId: Int = R.color.cvinl_text_special_color,
		val textDisabledColorResId: Int = R.color.cvinl_text_disabled_color,
		val textDisabledOtherMonthColorResId: Int = R.color.cvinl_text_disabled_other_month_color,
		val iconResId: Int? = null,
		val onClick: ((item: DayViewMdl) -> Unit?)? = null
){
	var isCurrentMonth: Boolean = false

	override fun equals(other: Any?): Boolean {
		if(other!=null && other is DayViewMdl){
			return model.calendar == other.model.calendar
		}
		return super.equals(other)
	}
}