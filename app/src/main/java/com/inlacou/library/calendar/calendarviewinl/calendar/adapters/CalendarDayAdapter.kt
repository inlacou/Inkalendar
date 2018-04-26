package com.inlacou.library.calendar.calendarviewinl.calendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayView
import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayViewMdl
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight

import java.util.ArrayList
import java.util.Calendar

/**
 * This class is responsible for loading a one day cell.
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 */
internal class CalendarDayAdapter(
		private val mCalendarPagerAdapter: CalendarPagerAdapter, context: Context,
		itemList: ArrayList<DayViewMdl>, pageMonth: Int) : ArrayAdapter<DayViewMdl>(context, R.layout.adapter_view_day, itemList) {

	private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
	private val mPageMonth: Int = if (pageMonth < 0) 11 else pageMonth
	private val mToday = Calendar.getInstance().toMidnight()

	override fun getView(position: Int, v: View?, parent: ViewGroup): View {
		var view = v
		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.adapter_view_day, parent, false)
		}

		val dayView = view!!.findViewById(R.id.view) as DayView
		dayView.model = getItem(position)

		return view
	}

	/*private fun setLabelColors(dayLabel: TextView, dayBack: View, day: Calendar) {
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
				if (!isCurrentMonthDay(day)) mCalendarProperties.anotherMonthsDaysLabelsColor
				else if (!isActiveDay(day)) mCalendarProperties.disabledDaysLabelsColor
				else if (isSpecialDay(day)) mCalendarProperties.specialDaysLabelsColor
				else mCalendarProperties.daysLabelsColor,
				Typeface.BOLD,
				if (isSpecialDay(day)) {
					if (isCurrentMonthDay(day)) mCalendarProperties.specialDaysLabelsBackgroundColor
					else mCalendarProperties.specialDisabledDaysLabelsBackgroundColor
				} else R.drawable.background_transparent)

		/*// Setting not current month day color
        if (!isCurrentMonthDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getAnotherMonthsDaysLabelsColor(),
                    Typeface.BOLD, R.drawable.background_transparent);
            return;
        }

        // Set view for all SelectedDays
        if (isSelectedDay(day)) {
            Stream.of(mCalendarPageAdapter.getSelectedDays())
                    .filter(selectedDay -> selectedDay.getCalendar().equals(day))
                    .findFirst()
                    .ifPresent(selectedDay -> selectedDay.setView(dayLabel));

            DayColorsUtils.setSelectedDayColors(dayLabel, mCalendarProperties);
            return;
        }

        // Setting disabled days color
        if (!isActiveDay(day)) {
            DayColorsUtils.setDayColors(dayLabel, mCalendarProperties.getDisabledDaysLabelsColor(),
                    Typeface.BOLD, R.drawable.background_transparent);
            return;
        }

        // Setting current month day color
        DayColorsUtils.setCurrentMonthDayColors(day, mToday, dayLabel, mCalendarProperties);*/
	}

	private fun isSelectedDay(day: Calendar): Boolean {
		return (mCalendarProperties.calendarType != CalendarView.CLASSIC && day.get(Calendar.MONTH) == mPageMonth
				&& mCalendarPageAdapter.selectedDays.contains(SelectedDay(day)))
	}

	private fun isCurrentMonthDay(day: Calendar): Boolean {
		return day.get(Calendar.MONTH) == mPageMonth && !(mCalendarProperties.minimumDate != null && day.before(mCalendarProperties.minimumDate) || mCalendarProperties.maximumDate != null && day.after(mCalendarProperties.maximumDate))
	}

	private fun isActiveDay(day: Calendar): Boolean {
		return !mCalendarProperties.disabledDays.contains(day)
	}

	private fun isSpecialDay(day: Calendar): Boolean =
			mCalendarProperties.eventDays
					.filter { it.isSpecial }
					.map { it.calendar }
					.contains(day)
*/
}
