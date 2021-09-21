package com.inlacou.library.calendar.inkalendar.views.calendar

import android.util.Log
import com.inlacou.library.calendar.inkalendar.*
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl
import java.util.*


class InkalendarCtrl(val view: Inkalendar, var model: InkalendarMdl) {

	fun onClick() {
		//TODO model.onClick?.invoke(model.copy(onClick = null))
	}

	private fun isScrollingLimited(calendar: Calendar, position: Int): Boolean {
		if (model.minimumDate.isMonthBefore(calendar)) {
			view.moveToNext(position)
			return true
		}

		if (model.maximumDate.isMonthAfter(calendar)) {
			view.moveToPrevious(position)
			return true
		}

		return false
	}

	fun onPageSelected(position: Int) {
		val calendar = model.today.clone() as Calendar
		calendar.add(Calendar.MONTH, position)

		if (!isScrollingLimited(calendar, position)) {
			view.setHeaderName(calendar)
			callOnPageChangeListeners(position)
		}
	}
	
	/**
	 * This method calls page change listeners after swipe calendar or click arrow buttons
	 */
	private fun callOnPageChangeListeners(position: Int) {
		val clone = model.today.clone() as Calendar
		clone.month = clone.month + position
		if (position>model.currentPage) {
			model.onForward?.invoke(clone)
		}
		if (position<model.currentPage) {
			model.onBackward?.invoke(clone)
		}

		model.currentPage = position
	}

	fun onPageLoad(fromTo: Pair<Calendar, Calendar>){
		model.onPageLoad?.invoke(fromTo)
	}

	fun Calendar.toDebug(): String {
		return "$dayOfMonth/$month/$year"
	}

	fun onDayClick(day: DayViewMdl) {
		if(!day.isCurrentMonth) {
			val anchor = model.anchor.clone() as Calendar
			val anchorPage = InkalendarMdl.FIRST_VISIBLE_PAGE
			val currentPage = model.currentPage
			val currentMonth = anchor.addMonths(currentPage-anchorPage)

			Log.d("DEBUG", "------------------------------")
			Log.d("DEBUG", "anchor: ${anchor.month} ${anchor.toDebug()}")
			Log.d("DEBUG", "clicked day month: ${day.model.calendar.month} ${day.model.calendar.toDebug()}")
			Log.d("DEBUG", "today month: ${model.today.month} ${model.today.toDebug()}")
			Log.d("DEBUG", "currentMonth: ${currentMonth.month} ${currentMonth.toDebug()}")
			Log.d("DEBUG", "currentPage: ${model.currentPage}")
			Log.d("DEBUG", "page is: " + when {
					day.model.calendar.month==currentMonth.month -> "same"
					day.model.calendar.month<currentMonth.month -> "previous"
					day.model.calendar.month>currentMonth.month -> "next"
					else -> "wtf"
				}
			)
			if(day.model.calendar.month<currentMonth.month) {
				view.moveToPrevious()
			}else if(day.model.calendar.month>currentMonth.month) {
				view.moveToNext()
			}
		}
		if(model.mode==InkalendarMdl.Mode.SINGLE_SELECTION) {
			model.selectedDays.clear()
			model.selectedDays.add(day.model.calendar)
			model.singleDaySelection?.invoke(day.model.calendar)
		}else{
			if (model.selectedDays.contains(day.model.calendar)) {
				model.selectedDays.remove(day.model.calendar)
			} else {
				model.selectedDays.add(day.model.calendar)
			}
			model.multiDaySelection?.invoke(model.selectedDays)
		}
		view.notifyDataSetChanged()
	}
}