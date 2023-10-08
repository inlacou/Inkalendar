package com.inlacou.library.calendar.inkalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.inlacou.inker.Inker
import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.month
import com.inlacou.library.calendar.inkalendar.toMidnight
import com.inlacou.library.calendar.inkalendar.toTimeDebug
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import com.inlacou.library.calendar.inkalendar.views.calendargrid.CalendarGridView
import com.inlacou.library.calendar.inkalendar.views.day.DayView
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl
import java.lang.ref.WeakReference

import java.util.Calendar

/**
 * This class is responsible for loading a one day cell.
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 * Forked by Inlacou on 26.04.2018.
 */
internal class CalendarDayAdapter(
	context: Context,
	val itemList: MutableList<DayViewMdl>,
	private val model: InkalendarMdl,
	currentMonth: Int
) : ArrayAdapter<DayViewMdl>(context, R.layout.inkalendar_adapter_view_day, itemList) {

	private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
	private val mPageMonth: Int = if (currentMonth < 0) 11 else currentMonth
	private val views = mutableListOf<WeakReference<DayView>>()


	fun notifyItemChanged(position: Int) {
		if(position<0 || position>=itemList.size) return
		Inker.d { "notifyItemChanged($position)" }
		views[position].get()?.model = getItem(position)!!.let {
			it.isSelected = model.selectedDays.map { it.toMidnight() }.contains(it.model.calendar)
			it.isCurrentMonth = isCurrentMonthDay(it.model.calendar)
			it
		}
	}

	override fun getView(position: Int, v: View?, parent: ViewGroup): View {
		Inker.d { "getView($position, ${if(v!=null) v::class.simpleName else "null"}, ${if(parent is CalendarGridView) "CalendarGridView-"+parent.position else parent::class.simpleName})" }
		val view = v ?: mLayoutInflater.inflate(R.layout.inkalendar_adapter_view_day, parent, false).also { views.add(WeakReference(it as DayView)) }

		val dayView = view.findViewById(R.id.view) as DayView
		getItem(position)?.let {
			it.isSelected = model.selectedDays.map { it.toMidnight() }.contains(it.model.calendar)
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

}
