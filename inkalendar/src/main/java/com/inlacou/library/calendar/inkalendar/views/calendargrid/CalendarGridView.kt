package com.inlacou.library.calendar.inkalendar.views.calendargrid

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView
import com.inlacou.inker.Inker
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
	
	private val days: MutableList<DayViewMdl> = mutableListOf()
	lateinit var calendarModel: InkalendarMdl
	var position: Int = 0
	var onClick: (item: DayViewMdl) -> Any? = {}

	//This method is needed to get wrap_content height for GridView
	public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2, MeasureSpec.AT_MOST))
	}

	/**
	 * This method fill calendar GridView with default days
	 *
	 * @param position Position of today page in ViewPager
	 */
	fun loadMonth() {
		Inker.d { "INKER - days (${days.size}): ${days.map { it.model.calendar.toDebugString() }}" }
		Inker.d { "INKER - calendarModel (${calendarModel.days.size}): ${calendarModel.days.map { it.calendar.toDebugString() }}" }
		adapter = CalendarDayAdapter(context, days, calendarModel, calendarModel.today.month)
	}

	fun getFromToDays(): Pair<Calendar, Calendar> {
		return Pair(days.first().model.calendar, days.last().model.calendar)
	}
	
	fun compute() {
		if(days.isNotEmpty()) {
			Inker.d { "INKER - days not empty (${days.size}): ${days.map { it.model.calendar.toDebugString() }}" }
		}
		days.clear()

		// Get Calendar object instance
		val calendar = calendarModel.today.clone() as Calendar

		// Add months to Calendar (a number of months depends on ViewPager position)
		calendar.add(Calendar.MONTH, position)

		val startingCal = calendar.clone() as Calendar
		
		// Set day of month as 1
		calendar.set(Calendar.DAY_OF_MONTH, 1)
		
		// Get a number of the first day of the first week
		val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
		
		// Count when month is beginning
		val monthBeginningCell = dayOfWeek + if (dayOfWeek == 1) 5 else -2
		
		// Subtract a number of beginning days, this will let it load a part of a previous month
		calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)
		val from = calendar.clone() as Calendar
		
		/*
        BreakPoints are:
            7, 14, 21, 28, 35 and 42 days
        Since months have at least 28 days, real breakpoints are 35 and 42
        And because it's weird to have an empty row sometimes, because some months need it (42) and some don't (35), real real breakpoint is always 42
         */
		this.addUntil(27, days, calendar) //...Since months have at least 28 days... (27+(1 from do->while))
		do {
			this.addDay(days, calendar)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		} while (
			calendar.sameYearAndMonth(startingCal)
		)

		this.addUntil(42, days, calendar) //real real breakpoint is always 42 (7 days * 6 rows)
		Inker.d { "INKER - end: ${days.size}" }
		Inker.d { "INKER - from: ${from.toDebugString()} | to: ${calendar.toDebugString()}" }
	}

	private fun addDay(days: MutableList<DayViewMdl>, calendar: Calendar) {
		//Inker.d { "INKER - days: ${days.size} | calendar: ${calendar.toDebugString()}" }
		val newCal = calendar.clone() as Calendar

		//val ts1 = System.currentTimeMillis()
		val dayInl = calendarModel.days.find { calendar.toMidnight()!! == it.calendar.toMidnight()!! }
		//Inker.e { "INKER - days.find in time: ${(System.currentTimeMillis()-ts1).toTime()}" }

		days.add(DayViewMdl(dayInl ?: DayInl(newCal)).apply {
			this.onClick = this@CalendarGridView.onClick
		})
	}

	fun Calendar.toDebugString() = "$year/${month + 1}/$dayOfMonth"

	private fun addUntil(number: Int, days: MutableList<DayViewMdl>, calendar: Calendar) {
		//Inker.d { "INKER - number: $number | days: ${days.size} | calendar: ${calendar.toDebugString()}" }
		while (days.size < number) {
			addDay(days, calendar)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		}
	}

	fun notifyDataSetChanged() {
		val ts1 = System.currentTimeMillis()
		compute()
		Inker.d { "INKER - compute in time: ${(System.currentTimeMillis()-ts1).toTime()}" }
		val ts2 = System.currentTimeMillis()
		loadMonth()
		Inker.d { "INKER - loadMonth in time: ${(System.currentTimeMillis()-ts2).toTime()}" }
	}

	private fun Long.toTime(): String {
		val millis = this%1000
		val seconds = (this/1000)%60
		val minutes: Int = (this/1000/60).toInt()
		return "$minutes:$seconds.$millis"
	}
}