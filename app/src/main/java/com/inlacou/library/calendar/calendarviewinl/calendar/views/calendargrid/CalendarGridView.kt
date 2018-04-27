package com.inlacou.library.calendar.calendarviewinl.calendar.views.calendargrid

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView
import com.inlacou.library.calendar.calendarviewinl.calendar.adapters.CalendarDayAdapter
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.immediatePreviousMonth
import com.inlacou.library.calendar.calendarviewinl.calendar.month
import com.inlacou.library.calendar.calendarviewinl.calendar.sameMonth
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import com.inlacou.library.calendar.calendarviewinl.calendar.views.calendar.CalendarViewInlMdl
import com.inlacou.library.calendar.calendarviewinl.calendar.views.day.DayViewMdl
import java.util.*

/**
 * Created by Mateusz Kornakiewicz on 15.11.2017.
 * Forked by Inlacou on 26.04.2018.
 */
class CalendarGridView @JvmOverloads constructor(
		context: Context,
		attrs: AttributeSet? = null,
		defStyleAttr: Int = 0
) : GridView(context, attrs, defStyleAttr) {

	lateinit var calendarModel: CalendarViewInlMdl
	var onClick: (item: DayViewMdl) -> Any? = {}

	//This method is needed to get wrap_content height for GridView
	public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
				View.MeasureSpec.AT_MOST)
		super.onMeasure(widthMeasureSpec, expandSpec)
	}

	/**
	 * This method fill calendar GridView with default days
	 *
	 * @param position Position of today page in ViewPager
	 */
	fun loadMonth(position: Int) {
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
            7, 14, 21, 28, 35 and 42 days
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

		val calendarDayAdapter = CalendarDayAdapter(context, days,
				calendarModel, startingCal.month)

		adapter = calendarDayAdapter
	}

	private fun addDay(days: ArrayList<DayViewMdl>, calendar: Calendar){
		val newCal = calendar.clone() as Calendar

		val day = calendarModel.days.find { calendar.toMidnight()!! == it.calendar.toMidnight()!! }

		val dayModel = if(day==null){
			DayViewMdl(DayInl(newCal))
		}else{
			DayViewMdl(day)
		}
		dayModel.onClick = {
			onClick.invoke(it)
		}

		days.add(dayModel)
	}

	private fun addUntil(number: Int, days: ArrayList<DayViewMdl>, calendar: Calendar) {
		while (days.size < number) {
			addDay(days, calendar)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		}
	}

	fun notifyDataSetChanged() {
		(adapter as CalendarDayAdapter).notifyDataSetChanged()
	}
}