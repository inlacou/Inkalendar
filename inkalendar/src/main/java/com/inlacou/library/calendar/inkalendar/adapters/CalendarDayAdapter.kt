package com.inlacou.library.calendar.inkalendar.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.month
import com.inlacou.library.calendar.inkalendar.toMidnight
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import com.inlacou.library.calendar.inkalendar.views.day.DayView
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl

import java.util.ArrayList
import java.util.Calendar

/**
 * This class is responsible for loading a one day cell.
 *
 * Created by Mateusz Kornakiewicz on 24.05.2017.
 * Forked by Inlacou on 26.04.2018.
 */
internal class CalendarDayAdapter(
		context: Context, itemList: ArrayList<DayViewMdl>,
		val model: InkalendarMdl, currentMonth: Int) : ArrayAdapter<DayViewMdl>(context, R.layout.adapter_view_day, itemList) {

	private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)
	private val mPageMonth: Int = if (currentMonth < 0) 11 else currentMonth

	override fun getView(position: Int, v: View?, parent: ViewGroup): View {
		var view = v
		if (view == null) {
			view = mLayoutInflater.inflate(R.layout.adapter_view_day, parent, false)
		}

		val dayView = view!!.findViewById(R.id.view) as DayView
		val viewModel = getItem(position)

		viewModel.isSelected = isSelected(viewModel.model.calendar)
		viewModel.isCurrentMonth = isCurrentMonthDay(viewModel.model.calendar)

		dayView.model = viewModel

		return view
	}

	private fun isCurrentMonthDay(day: Calendar): Boolean {
		return day.month == mPageMonth
				&&
				!(model.minimumDate != null && day.before(model.minimumDate)
						|| model.maximumDate != null && day.after(model.maximumDate))
	}

	private fun isSelected(day: Calendar): Boolean = model.selectedDays.map { it.toMidnight()!! }.contains(day)

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

		/*// Setting not today month calendar color
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

        // Setting today month calendar color
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
