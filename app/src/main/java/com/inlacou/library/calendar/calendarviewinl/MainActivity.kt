package com.inlacou.library.calendar.calendarviewinl

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.inlacou.inker.Inker
import com.inlacou.library.calendar.inkalendar.*
import com.inlacou.library.calendar.inkalendar.business.DayInl
import com.inlacou.library.calendar.inkalendar.views.calendar.Inkalendar
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

	@SuppressLint("CheckResult")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		Inker.mix(DebugColor())
		Inker.d { "INKER - ---------------------------------------------------" }

		val days = mutableListOf<DayInl>()
		val inkalendar = findViewById<Inkalendar>(R.id.calendarView)
		inkalendar.model = InkalendarMdl(
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
		Observable.timer(3, TimeUnit.SECONDS).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe {
			val ts = System.currentTimeMillis()
			days.add(DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false)) //Disabled
			days.add(DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false)) //Disabled
			days.add(DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true)) //Enabled
			days.add(DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true)) //Special
			days.add(DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader, colorHex = "#00FF00")) //Menacing space invader as icon
			days.add(DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow)) //Special day when menacing space invader attacks

			val startFrom = 1000
			repeat(1000) {
				days.add(DayInl(calendar = Calendar.getInstance().addDays(startFrom+it), iconResId = R.drawable.space_invader, colorHex = "#00FF00")) //Menacing space invader as icon
			}

			Inker.d { "INKER - added ${days.size} (from today+$startFrom) days to list: ${(System.currentTimeMillis()-ts).toTime()}" }
			inkalendar.notifyDataSetChanged()
			Inker.d { "INKER - added ${days.size} (from today+$startFrom) days to calendar: ${(System.currentTimeMillis()-ts).toTime()}" }
			Toast.makeText(this, "loaded days asynchronously!", Toast.LENGTH_SHORT).show()
		}
	}

	private fun Long.toTime(): String {
		val millis = this%1000
		val seconds = (this/1000)%60
		val minutes: Int = (this/1000/60).toInt()
		return "$minutes:$seconds.$millis"
	}
}
