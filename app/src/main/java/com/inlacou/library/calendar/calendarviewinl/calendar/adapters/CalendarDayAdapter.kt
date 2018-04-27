package com.inlacou.library.calendar.calendarviewinl.calendar.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayView
import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayViewMdl
import com.inlacou.library.calendar.calendarviewinl.calendar.month
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import com.inlacou.library.calendar.calendarviewinl.calendar.view.CalendarViewInlMdl

import java.util.ArrayList
import java.util.Calendar

/**
 * This class is responsible for loading a one day cell.
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */
internal class CalendarDayAdapter(
		private val mCalendarPagerAdapter: CalendarPagerAdapter, context: Context,
		itemList: ArrayList<DayViewMdl>, val model: CalendarViewInlMdl, currentMonth: Int) : ArrayAdapter<DayViewMdl>(context, R.layout.adapter_view_day, itemList) {

	private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
	private val mPageMonth: Int = if (currentMonth < 0) 11 else currentMonth
	private val mToday = Calendar.getInstance().toMidnight()

	override fun getView(position: Int, v: View?, parent: ViewGroup): View {
		Log.d("CalendarDayAdapter", "getView | position: $position | month: $mPageMonth")
		var view = v
		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.adapter_view_day, parent, false)
		}

		val dayView = view!!.findViewById(R.id.view) as DayView
		val viewModel = getItem(position)

		viewModel.isCurrentMonth = isCurrentMonthDay(viewModel.calendar)

		dayView.model = viewModel

		return view
	}

	private fun isCurrentMonthDay(day: Calendar): Boolean {
		return day.month == mPageMonth
				&&
				!(model.minimumDate != null && day.before(model.minimumDate)
						|| model.maximumDate != null && day.after(model.maximumDate))
	}

	/*private fun setLabelColors(dayLabel: TextView, dayBack: View, calendar: Calendar) {
		/*
	    text.color =
		    if(otherMonth) grey
		    if(special) white
		    if(disabled) red
		    else black
	    text.back =
	        if(special) red
	        else transparent
	    back =
	        if(selected) grey
	        else transparent
	    icon -> icon
	    */

		// still TODO selected state

		DayColorsUtils.setDayColors(dayLabel,
				if (!isCurrentMonthDay(calendar)) mCalendarProperties.anotherMonthsDaysLabelsColor
				else if (!isActiveDay(calendar)) mCalendarProperties.disabledDaysLabelsColor
				else if (isSpecialDay(calendar)) mCalendarProperties.specialDaysLabelsColor
				else mCalendarProperties.daysLabelsColor,
				Typeface.BOLD,
				if (isSpecialDay(calendar)) {
					if (isCurrentMonthDay(calendar)) mCalendarProperties.specialDaysLabelsBackgroundColor
					else mCalendarProperties.specialDisabledDaysLabelsBackgroundColor
				} else R.drawable.background_transparent)

		/*// Setting not current month calendar color
        if (!isCurrentMonthDay(calendar)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.BOLD, R.drawable.background_transparent);
            return;
        }

        // Set view for all SelectedDays
        if (isSelectedDay(calendar)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(calendar))
                    .findFirst()
                    .ifPresent(selectedDay -> selectedDay.setView(dayLabel));

            DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
            return;
        }

        // Setting disabled days color
        if (!isActiveDay(calendar)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getDisabledDaysLabelsColor(),
                    Typeface.BOLD, R.drawable.background_transparent);
            return;
        }

        // Setting current month calendar color
        DayColorsUtils.setCurrentMonthDayColors(calendar, mToday, dayLabel, mCalendarProperties);*/
	}

	private fun isSelectedDay(calendar: Calendar): Boolean {
		return (mCalendarProperties.calendarType != CalendarView.CLASSIC && calendar.get(Calendar.MONTH) == mPageMonth
				&& mCalendarPageAdapter.selectedDays.contains(SelectedDay(calendar)))
	}



	private fun isActiveDay(calendar: Calendar): Boolean {
		return !mCalendarProperties.disabledDays.contains(calendar)
	}

	private fun isSpecialDay(calendar: Calendar): Boolean =
			mCalendarProperties.eventDays
					.filter { it.isSpecial }
					.map { it.calendar }
					.contains(calendar)
*/
}
