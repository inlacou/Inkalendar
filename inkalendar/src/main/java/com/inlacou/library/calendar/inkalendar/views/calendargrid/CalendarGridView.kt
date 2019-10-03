package com.inlacou.library.calendar.inkalendar.views.calendargrid

import android.content.Context
import android.text.style.TtsSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.GridView
import com.inlacou.library.calendar.inkalendar.*
import com.inlacou.library.calendar.inkalendar.adapters.CalendarDayAdapter
import com.inlacou.library.calendar.inkalendar.business.DayInl
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl
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
	
	private var days: ArrayList<DayViewMdl> = ArrayList()
	lateinit var calendarModel: InkalendarMdl
	var position: Int = 0
	var onClick: (item: DayViewMdl) -> Any? = {}

	//This method is needed to get wrap_content height for GridView
	public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
				MeasureSpec.AT_MOST)
		super.onMeasure(widthMeasureSpec, expandSpec)
	}

	/**
	 * This method fill calendar GridView with default days
	 *
	 * @param position Position of today page in ViewPager
	 */
	fun loadMonth() {
		Log.d("loadMonth", "days.first: ${days.first().model.calendar.dayOfMonth}/${days.first().model.calendar.month}")
		Log.d("loadMonth", "days.last: ${days.last().model.calendar.dayOfMonth}/${days.last().model.calendar.month}")
		Log.d("loadMonth", "calendarModel.today.month: ${(calendarModel.today.clone() as Calendar).month}")
		adapter = CalendarDayAdapter(context, days, calendarModel, (calendarModel.today.clone() as Calendar).month)
	}

	fun getFromToDays(): Pair<Calendar, Calendar> {
		Log.d("getFromToDays", "days.first: ${days.first().model.calendar}")
		Log.d("getFromToDays", "days.last: ${days.last().model.calendar}")
		Log.d("getFromToDays", "calendarModel.today.month: ${(calendarModel.today.clone() as Calendar).month}")
		return Pair(days.first().model.calendar, days.last().model.calendar)
	}
	
	fun compute(){
		days.clear()
		
		// Get Calendar object instance
		val calendar = calendarModel.today.clone() as Calendar
		
		// Add months to Calendar (a number of months depends on ViewPager position)
		Log.d("compute", "position: $position")
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
        Since months have at least 28 days, real breakpoints are 35 and 42
         */
		addUntil(27, days, calendar) //...Since months have at least 28 days... (27+(1 from do->while))
		do {
			addDay(days, calendar)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		} while (
				calendar.sameMonth(startingCal)
		)
		
		if (startingCal.immediatePreviousMonth(calendar) && days.size > 28 && days.size < 35) { //35 breakpoint
			addUntil(35, days, calendar) //...real breakpoints are 35...
		} else if (startingCal.immediatePreviousMonth(calendar) && days.size > 35 && days.size < 42) { //42 breakpoint
			addUntil(42, days, calendar) //...and 42...
		}
	}

	private fun addDay(days: ArrayList<DayViewMdl>, calendar: Calendar){
		val newCal = calendar.clone() as Calendar

		val day = calendarModel.days.find { calendar.toMidnight()!! == it.calendar.toMidnight()!! }

		val dayModel = if(day==null) {
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
		compute()
		loadMonth()
	}
}