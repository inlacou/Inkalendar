package com.inlacou.library.calendar.calendarviewinl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.inlacou.library.calendar.calendarviewinl.R.id.calendarView
import com.inlacou.library.calendar.inkalendar.*
import com.inlacou.library.calendar.inkalendar.business.DayInl
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		val days = mutableListOf<DayInl>(
				//DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false) //Disabled
				//, DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false) //Disabled
				//, DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true) //Enabled
				//, DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true) //Special
				//, DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader) //Menacing space invader as icon
				//, DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader) //Special day when menacing space invader attacks
		)

		calendarView.model = InkalendarMdl(
				today = (Calendar.getInstance().clone() as Calendar) //.addMonths(1).addYears(1), //Set starting day (default to *today*)
				, mode = InkalendarMdl.Mode.SINGLE_SELECTION,
				days = days,
				selectedDays = mutableListOf(
						Calendar.getInstance()//.addDays(5)
						//, Calendar.getInstance().addDays(7)
				),
				singleDaySelection = {
					Toast.makeText(this, "selected ${it.dayOfMonth}, ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
				},
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

		//Load data "asynchronously"
		Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
			days.add(DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false)) //Disabled
			days.add(DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false)) //Disabled
			days.add(DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true)) //Enabled
			days.add(DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true)) //Special
			days.add(DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader, colorHex = "#00FF00")) //Menacing space invader as icon
			days.add(DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow)) //Special day when menacing space invader attacks
			calendarView.notifyDataSetChanged()
			Toast.makeText(this, "loaded days asynchronously!", Toast.LENGTH_SHORT).show()
		}
	}
}
