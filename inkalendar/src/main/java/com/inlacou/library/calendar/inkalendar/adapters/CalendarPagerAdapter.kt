package com.inlacou.library.calendar.inkalendar.adapters

import android.content.Context
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl

import com.inlacou.library.calendar.inkalendar.views.calendargrid.CalendarGridView
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl

import java.util.Calendar

/**
 * This class is responsible for loading a calendar page content.
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 * Forked by Inlacou on 26.04.2018.
 */
class CalendarPagerAdapter(
		private val mContext: Context,
		private val calendarModel: InkalendarMdl,
		var onClick: (item: DayViewMdl) -> Any?,
		var onInstantiate: (item: Pair<Calendar, Calendar>) -> Any?
) : androidx.viewpager.widget.PagerAdapter() {

	val gridViews: MutableList<CalendarGridView> = mutableListOf()

	override fun getCount(): Int {
		return CALENDAR_SIZE
	}

	override fun getItemPosition(`object`: Any): Int {
		return POSITION_NONE
	}

	override fun isViewFromObject(view: View, `object`: Any): Boolean {
		return view === `object`
	}

	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val calendarGridView = inflater.inflate(R.layout.calendar_view_grid, null) as CalendarGridView

		//set data
		calendarGridView.calendarModel = calendarModel
		calendarGridView.position = position
		//Work (with data)
		calendarGridView.compute()
		calendarGridView.loadMonth()
		calendarGridView.getFromToDays()
		calendarGridView.onClick = {
			onClick.invoke(it)
		}
		onInstantiate.invoke(calendarGridView.getFromToDays())

		container.addView(calendarGridView)
		gridViews.add(calendarGridView)
		return calendarGridView
	}

	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
		gridViews.remove(`object` as CalendarGridView)
		container.removeView(`object` as View)
	}

	fun notifyDataSetChanged(complete: Boolean) {
		gridViews.forEach { it.notifyDataSetChanged() }
		if(complete) super.notifyDataSetChanged()
	}

	companion object {

		/**
		 * A number of months (pages) in the calendar
		 * 2401 months means 1200 months (100 years) before and 1200 months (100 years) after the today month
		 */
		const val CALENDAR_SIZE = 2401
	}
}