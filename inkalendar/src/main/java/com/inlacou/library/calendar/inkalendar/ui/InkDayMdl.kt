package com.inlacou.library.calendar.inkalendar.ui

import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.business.Day

data class InkDayMdl @JvmOverloads constructor(
    val model: Day = Day(),
    val selectedBackResId: Int? = R.drawable.inkalendar_selected_day_background,
    val specialTextBackColorResId: Int? = R.color.inkalendar_back_disabled_color,
    val textNormalColorResId: Int = R.color.inkalendar_text_normal_color,
    val textSelectedColorResId: Int = R.color.inkalendar_text_selected_color,
    val textSpecialColorResId: Int = R.color.inkalendar_text_special_color,
    val textDisabledColorResId: Int = R.color.inkalendar_text_disabled_color,
    val textDisabledOtherMonthColorResId: Int = R.color.inkalendar_text_disabled_other_month_color,
    var onClick: ((item: InkDayMdl) -> Any?)? = null
) {
	var isSelected: Boolean = false
	var isCurrentMonth: Boolean = false

	override fun equals(other: Any?): Boolean {
		if(other!=null && other is InkDayMdl){
			return model.date == other.model.date
		}
		return super.equals(other)
	}
}