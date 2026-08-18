package com.inlacou.library.calendar.inkalendar.ui

import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.business.Day

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
