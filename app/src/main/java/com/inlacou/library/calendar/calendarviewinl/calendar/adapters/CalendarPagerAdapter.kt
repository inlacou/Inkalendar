package com.inlacou.library.calendar.calendarviewinl.calendar.adapters

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.*
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayViewMdl
import com.inlacou.library.calendar.calendarviewinl.calendar.view.CalendarViewInlMdl

import java.util.ArrayList
import java.util.Calendar

/**
 * This class is responsible for loading a calendar page content.
 *
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 * Forked by Inlacou on 26.04.2018.
 */
class CalendarPagerAdapter(private val mContext: Context, val calendarModel: CalendarViewInlMdl) : PagerAdapter() {
	private var mCalendarGridView: CalendarGridView? = null

	override fun getCount(): Int {
		return CALENDAR_SIZE
	}

	override fun getItemPosition(`object`: Any): Int {
		return PagerAdapter.POSITION_NONE
	}

	override fun isViewFromObject(view: View, `object`: Any): Boolean {
		return view === `object`
	}

	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		mCalendarGridView = inflater.inflate(R.layout.calendar_view_grid, null) as CalendarGridView

		loadMonth(position)

		container.addView(mCalendarGridView)
		return mCalendarGridView as CalendarGridView
	}

	/**
	 * This method fill calendar GridView with default days
	 *
	 * @param position Position of today page in ViewPager
	 */
	private fun loadMonth(position: Int) {
		val days = ArrayList<DayViewMdl>()

		// Get Calendar object instance
		val calendar = calendarModel.today.clone() as Calendar

		// Add months to Calendar (a number of months depends on ViewPager position)
		calendar.add(Calendar.MONTH, position)

		val startingCal = calendar.clone() as Calendar

		// Set day of month as 1
		calendar.set(Calendar.DAY_OF_MONTH, 1)

		// Get a number of the first day of the week
		val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

		// Count when month is beginning
		val monthBeginningCell = dayOfWeek + if (dayOfWeek == 1) 5 else -2

		// Subtract a number of beginning days, it will let to load a part of a previous month
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)

		/*
        BreakPoints are:
            7 days
            14 days
            21 days
            28 days
            35 days
            42 days
        Since months are at least 28 days, real breakpoints are 35 and 42
         */
		do {
			addDay(days, calendar)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		} while (
				calendar.immediatePreviousMonth(startingCal) ||
				calendar.sameMonth(startingCal)
		)

		if (startingCal.immediatePreviousMonth(calendar) && days.size > 28 && days.size < 35) { //35 breakpoint
			addUntil(35, days, calendar)
		} else if (startingCal.immediatePreviousMonth(calendar) && days.size > 35 && days.size < 42) { //42 breakpoint
			addUntil(42, days, calendar)
		}

		val calendarDayAdapter = CalendarDayAdapter(mContext, days,
				calendarModel, startingCal.month)

		mCalendarGridView!!.adapter = calendarDayAdapter
	}

	private fun addDay(days: ArrayList<DayViewMdl>, calendar: Calendar){
		val newCal = calendar.clone() as Calendar

		val day = calendarModel.days.find { calendar.toMidnight()!! == it.calendar.toMidnight()!! }

		if(day==null){
			days.add(DayViewMdl(DayInl(newCal)))
		}else{
			Log.d("DEBUG.addDay", "loading day from calendarModel!")
			days.add(DayViewMdl(day))
		}
	}

	private fun addUntil(number: Int, days: ArrayList<DayViewMdl>, calendar: Calendar) {
		while (days.size < number) {
			addDay(days, calendar)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		}
	}

	override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
		container.removeView(`object` as View)
	}

	companion object {

		/**
		 * A number of months (pages) in the calendar
		 * 2401 months means 1200 months (100 years) before and 1200 months after the today month
		 */
		val CALENDAR_SIZE = 2401
	}
}