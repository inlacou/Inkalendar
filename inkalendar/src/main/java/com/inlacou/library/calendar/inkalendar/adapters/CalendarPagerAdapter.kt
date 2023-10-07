package com.inlacou.library.calendar.inkalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inlacou.inker.Inker
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

	private val gridViews: MutableList<CalendarGridView> = mutableListOf()

	override fun getCount(): Int = CALENDAR_SIZE

	override fun getItemPosition(item: Any): Int = POSITION_NONE

	override fun isViewFromObject(view: View, `object`: Any): Boolean = view === `object`

	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		Inker.d { "INKER - $position" }

		val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val calendarGridView = inflater.inflate(R.layout.inkalendar_view_grid, null) as CalendarGridView

		//set data
		calendarGridView.calendarModel = calendarModel
		calendarGridView.position = position
		//Work (with data)
		calendarGridView.compute()
		calendarGridView.loadMonth()
		calendarGridView.onClick = {
			onClick.invoke(it)
		}
		onInstantiate.invoke(calendarGridView.getFromToDays())

		container.addView(calendarGridView)
		gridViews.add(calendarGridView)
		return calendarGridView
	}

	override fun destroyItem(container: ViewGroup, position: Int, item: Any) {
		Inker.d { "INKER - position: $position" }
		gridViews.remove(item as CalendarGridView)
		container.removeView(item as View)
	}

	fun notifyDataSetChanged(complete: Boolean) {
		Inker.d { "INKER - complete: $complete" }
		gridViews.forEach {
			val ts = System.currentTimeMillis()
			it.notifyDataSetChanged()
			Inker.d { "INKER - notified gridview in time: ${(System.currentTimeMillis()-ts).toTime()}" }
		}
		val ts = System.currentTimeMillis()
		if(complete) super.notifyDataSetChanged()
		Inker.d { "INKER - notified super in time: ${(System.currentTimeMillis()-ts).toTime()}" }
	}

	private fun Long.toTime(): String {
		val millis = this%1000
		val seconds = (this/1000)%60
		val minutes: Int = (this/1000/60).toInt()
		return "$minutes:$seconds.$millis"
	}

	companion object {

		/**
		 * A number of months (pages) in the calendar
		 * 2401 months means 1200 months (100 years) before and 1200 months (100 years) after the today month
		 */
		const val CALENDAR_SIZE = 2401
	}
}