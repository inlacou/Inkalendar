package com.inlacou.library.calendar.inkalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.month
import com.inlacou.library.calendar.inkalendar.toMidnight
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import com.inlacou.library.calendar.inkalendar.views.day.DayView
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl

import java.util.ArrayList
import java.util.Calendar

/**
 * This class is responsible for loading a one day cell.
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 * Forked by Inlacou on 26.04.2018.
 */
internal class CalendarDayAdapter(
		context: Context, itemList: ArrayList<DayViewMdl>,
		val model: InkalendarMdl, currentMonth: Int) : ArrayAdapter<DayViewMdl>(context, R.layout.adapter_view_day, itemList) {

	private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
	private val mPageMonth: Int = if (currentMonth < 0) 11 else currentMonth

	override fun getView(position: Int, v: View?, parent: ViewGroup): View {
		val view = v ?: mLayoutInflater.inflate(R.layout.adapter_view_day, parent, false)

		val dayView = view.findViewById(R.id.view) as DayView
		getItem(position)?.let {
			it.isSelected = isSelected(it.model.calendar)
			it.isCurrentMonth = isCurrentMonthDay(it.model.calendar)
			dayView.model = it
		}

		return view
	}

	private fun isCurrentMonthDay(day: Calendar): Boolean {
		return day.month == mPageMonth
				&&
				!(model.minimumDate != null && day.before(model.minimumDate)
						|| model.maximumDate != null && day.after(model.maximumDate))
	}

	private fun isSelected(day: Calendar): Boolean = model.selectedDays.map { it.toMidnight()!! }.contains(day)

}
