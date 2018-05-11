package com.inlacou.library.calendar.calendarviewinl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.inlacou.library.calendar.calendarviewinl.calendar.*
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.views.calendar.CalendarViewInlMdl
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val days = mutableListOf(
				DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false) //Disabled
				, DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false) //Disabled
				, DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true) //Enabled
				, DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true) //Special
				, DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader) //Menacing space invader as icon
				, DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader) //Special day when menacing space invader attacks
		)

		calendarView.model = CalendarViewInlMdl(
				//today = Calendar.getInstance().addMonths(1).addYears(1), //Set starting day (default to *today*)
				days = days,
				selectedDays = mutableListOf(
						Calendar.getInstance().addDays(5)
						, Calendar.getInstance().addDays(7)
				),
				onBackward = {
					Toast.makeText(this, "onBackward ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
				},
				onForward = {
					Toast.makeText(this, "onForward ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
				},
				onPageLoad = {
					Toast.makeText(this,
							"newPage from ${it.first.dayOfMonth}/${it.first.month}/${it.first.year} " +
									"to ${it.second.dayOfMonth}/${it.second.month}/${it.second.year}",
							Toast.LENGTH_SHORT).show()
				})
	}
}
