package com.inlacou.library.calendar.inkalendar.views.calendargrid

import android.content.Context
import android.util.AttributeSet
import android.view.View
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

	private var from: Calendar? = null
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
	 */
	fun loadMonth() {
		Inker.d { "loadMonth-$position" }
		adapter = CalendarDayAdapter(context, days, calendarModel, (calendarModel.today.clone() as Calendar).apply { add(Calendar.MONTH, position) }.month)
	}

	fun getFromToDays(): Pair<Calendar, Calendar> {
		return Pair(days.first().model.calendar, days.last().model.calendar)
	}

	fun compute() {
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
		if(from == null) from = calendar.clone() as Calendar

		val filteredDays: List<DayInl> = calendarModel.days.filter { filter(it.calendar, from!!) }

		/*
        BreakPoints are:
            7, 14, 21, 28, 35 and 42 days
        Since months have at least 28 days, real breakpoints are 35 and 42
        And because it's weird to have an empty row sometimes, because some months need it (42) and some don't (35), real real breakpoint is always 42
         */
		this.addUntil(27, days, calendar, filteredDays) //...Since months have at least 28 days... (27+(1 from do->while))
		do {
			this.addDay(days, calendar, filteredDays)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		} while (
			calendar.sameYearAndMonth(startingCal)
		)

		this.addUntil(42, days, calendar, filteredDays) //real real breakpoint is always 42 (7 days * 6 rows)
	}

	private fun filter(item: Calendar, start: Calendar): Boolean = item.timeInMillis>start.timeInMillis && item.timeInMillis<start.timeInMillis+3628800000L

	private fun addDay(days: MutableList<DayViewMdl>, calendar: Calendar, modelDays: List<DayInl>) {
		val newCal = calendar.clone() as Calendar

		val dayInl = modelDays.find { calendar.toMidnight() == it.calendar.toMidnight() }

		days.add(DayViewMdl(dayInl ?: DayInl(newCal)).apply {
			this.onClick = {
				this@CalendarGridView.onClick.invoke(it)
			}
		})
	}

	private fun addUntil(number: Int, days: MutableList<DayViewMdl>, calendar: Calendar, modelDays: List<DayInl>) {
		while (days.size < number) {
			addDay(days, calendar, modelDays)
			calendar.add(Calendar.DAY_OF_MONTH, 1)
		}
	}

	fun notifyDataSetChanged() {
		Inker.d { "notifyDataSetChanged-$position" }
		compute()
		loadMonth()
	}

	fun updateSelected(unselectedDays: List<Calendar>, selectedDays: List<Calendar>) {
		Inker.d { "updateSelected-$position | unselectedDays: ${unselectedDays.map { it.toTimeDebug() }}" }
		Inker.d { "updateSelected-$position | selectedDays: ${selectedDays.map { it.toTimeDebug() }}" }
		Inker.d { "updateSelected-$position | filter using ${from?.toTimeDebug()}" }
		selectedDays
			.filter { it.year==from?.year }
			.forEach { (adapter as CalendarDayAdapter).notifyItemChanged(it.positionOnList()) }
		unselectedDays
			.filter { it.year==from?.year }
			.forEach { (adapter as CalendarDayAdapter).notifyItemChanged(it.positionOnList()) }
	}

	private fun Calendar.positionOnList() = dayOfYear-from!!.dayOfYear
}