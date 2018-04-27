package com.inlacou.library.calendar.calendarviewinl

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.inlacou.library.calendar.calendarviewinl.calendar.addDays
import com.inlacou.library.calendar.calendarviewinl.calendar.business.DayInl
import com.inlacou.library.calendar.calendarviewinl.calendar.view.CalendarViewInlMdl
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		calendarView.model = CalendarViewInlMdl(days = mutableListOf(
				DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false),
				DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false),
				DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true)))
	}
}
