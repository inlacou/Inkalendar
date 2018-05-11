package com.inlacou.library.calendar.calendarviewinl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.inlacou.library.calendar.calendarviewinl.calendar.addDays
import com.inlacou.library.calendar.calendarviewinl.calendar.addMonths
import com.inlacou.library.calendar.calendarviewinl.calendar.addYears
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.toMidnight
import com.inlacou.library.calendar.calendarviewinl.calendar.views.calendar.CalendarViewInlMdl
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		calendarView.model = CalendarViewInlMdl(
				//today = Calendar.getInstance().addMonths(1).addYears(1), //Set starting day (default to *today*)
				days = mutableListOf(
						DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false) //Disabled
						, DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false) //Disabled
						, DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true) //Enabled
						, DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true) //Special
						, DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader) //Menacing space invader as icon
						, DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader) //Special day when menacing space invader attacks
				),
				selectedDays = mutableListOf(
						Calendar.getInstance().addDays(5)
						, Calendar.getInstance().addDays(7)
				),
				onBackward = {
					Toast.makeText(this, "onBackward", Toast.LENGTH_SHORT).show()
				},
				onForward = {
					Toast.makeText(this, "onForward", Toast.LENGTH_SHORT).show()
				}
		)
	}
}
