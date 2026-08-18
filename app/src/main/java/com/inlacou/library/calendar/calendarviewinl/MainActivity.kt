package com.inlacou.library.calendar.calendarviewinl

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.inlacou.inker.Inker
import com.inlacou.library.calendar.inkalendar.*
import com.inlacou.library.calendar.inkalendar.business.DayInl
import com.inlacou.library.calendar.inkalendar.compose.Inkalendar
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.Calendar
import java.util.concurrent.TimeUnit
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

	@SuppressLint("CheckResult")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Inker.mix(DebugColor())
		Inker.d { "INKER - ---------------------------------------------------" }

		setContent {
			val days = remember { mutableStateListOf<DayInl>() }
			val selectedDays = remember { mutableStateListOf<Calendar>(Calendar.getInstance().toMidnight()) }

			val model = remember(days, selectedDays) {
				InkalendarMdl(
					today = Calendar.getInstance().toMidnight(),
					mode = InkalendarMdl.Mode.SINGLE_SELECTION,
					days = days,
					selectedDays = selectedDays,
					singleDaySelection = {
						Toast.makeText(this, "selected ${it.dayOfMonth}, ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
					},
					onBackward = {
						Toast.makeText(this, "onBackward ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
					},
					onForward = {
						Toast.makeText(this, "onForward ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
					},
					onPageLoad = { fromTo ->
						Toast.makeText(this,
							"newPage from ${fromTo.first.dayOfMonth}/${fromTo.first.month}/${fromTo.first.year} " +
									"to ${fromTo.second.dayOfMonth}/${fromTo.second.month}/${fromTo.second.year}",
							Toast.LENGTH_SHORT).show()
					}
				)
			}

			Inkalendar(
				modifier = Modifier.fillMaxSize(),
				model = model,
				onDayClick = { day ->
					if (model.mode == InkalendarMdl.Mode.SINGLE_SELECTION) {
						selectedDays.clear()
						selectedDays.add(day)
						model.singleDaySelection?.invoke(day)
					} else {
						var found = false
						val iterator = selectedDays.iterator()
						while(iterator.hasNext()){
							if(iterator.next().toMidnight() == day.toMidnight()){
								iterator.remove()
								found = true
								break
							}
						}
						if(!found) selectedDays.add(day)
						model.multiDaySelection?.invoke(selectedDays)
					}
				}
			)

			LaunchedEffect(Unit) {
				//Load data "asynchronously"
				io.reactivex.rxjava3.core.Observable.timer(3, TimeUnit.SECONDS)
					.subscribeOn(Schedulers.newThread())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe {
						val ts = System.currentTimeMillis()
						val newDays = ArrayList<DayInl>()
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader, colorHex = "#00FF00"))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(-28), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(-29), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(-27), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(-27), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(-27), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
						newDays.add(DayInl(calendar = Calendar.getInstance().addDays(-38), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))

						val startFrom = 1000
						for (i in 0..999) {
							newDays.add(DayInl(calendar = Calendar.getInstance().addDays(startFrom + i), iconResId = R.drawable.space_invader, colorHex = "#00FF00"))
						}

						days.addAll(newDays)

						Inker.d { "INKER - added ${days.size} (from today+$startFrom) days to list: ${(System.currentTimeMillis()-ts).toTime()}" }
						Toast.makeText(this@MainActivity, "loaded days asynchronously!", Toast.LENGTH_SHORT).show()
					}
			}
		}
	}

	private fun Long.toTime(): String {
		val millis = this%1000
		val seconds = (this/1000)%60
		val minutes: Int = (this/1000/60).toInt()
		return "$minutes:$seconds.$millis"
	}
}
