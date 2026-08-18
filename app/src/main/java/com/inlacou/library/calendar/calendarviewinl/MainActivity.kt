package com.inlacou.library.calendar.calendarviewinl

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.inlacou.inker.Inker
import com.inlacou.library.calendar.inkalendar.business.Day
import com.inlacou.library.calendar.inkalendar.ui.Inkalendar
import com.inlacou.library.calendar.inkalendar.ui.InkalendarMdl
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.ArrayList
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {

	@SuppressLint("CheckResult")
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Inker.mix(DebugColor())
		Inker.d { "INKER - ---------------------------------------------------" }

		setContent {
			MaterialTheme {
				val days = remember { mutableStateListOf<Day>() }
				val selectedDays = remember { mutableStateListOf<LocalDate>(LocalDate.now()) }

				val model = remember(days, selectedDays) {
					InkalendarMdl(
						today = LocalDate.now(),
						mode = InkalendarMdl.Mode.SINGLE_SELECTION,
						days = days,
						selectedDays = selectedDays,
						singleDaySelection = {
							Toast.makeText(this, "selected ${it.dayOfMonth}, ${it.monthValue} of ${it.year}", Toast.LENGTH_SHORT).show()
						},
						onBackward = {
							Toast.makeText(this, "onBackward ${it.monthValue} of ${it.year}", Toast.LENGTH_SHORT).show()
						},
						onForward = {
							Toast.makeText(this, "onForward ${it.monthValue} of ${it.year}", Toast.LENGTH_SHORT).show()
						},
						onPageLoad = { fromTo ->
							Toast.makeText(this,
								"newPage from ${fromTo.first.dayOfMonth}/${fromTo.first.monthValue}/${fromTo.first.year} " +
										"to ${fromTo.second.dayOfMonth}/${fromTo.second.monthValue}/${fromTo.second.year}",
								Toast.LENGTH_SHORT).show()
						}
					)
				}

				Scaffold(
					topBar = {
						TopAppBar(
							title = { Text("Inkalendar") },
							backgroundColor = MaterialTheme.colors.primary,
							contentColor = MaterialTheme.colors.onPrimary,
							modifier = Modifier.statusBarsPadding()
						)
					}
				) { paddingValues ->
					Inkalendar(
						modifier = Modifier
							.fillMaxSize()
							.padding(paddingValues),
						model = model,
						onDayClick = { day ->
							if (model.mode == InkalendarMdl.Mode.SINGLE_SELECTION) {
								selectedDays.clear()
								selectedDays.add(day)
								model.singleDaySelection?.invoke(day)
							} else {
								if (selectedDays.contains(day)) {
									selectedDays.remove(day)
								} else {
									selectedDays.add(day)
								}
								model.multiDaySelection?.invoke(selectedDays)
							}
						}
					)
				}

				LaunchedEffect(Unit) {
					//Load data "asynchronously"
					delay(3000.milliseconds)
					val ts = System.currentTimeMillis()
					val newDays = ArrayList<Day>()
					newDays.add(Day(date = LocalDate.now().plusDays(1), isEnabled = false))
					newDays.add(Day(date = LocalDate.now().plusDays(2), isEnabled = false))
					newDays.add(Day(date = LocalDate.now().plusDays(3), isEnabled = true))
					newDays.add(Day(date = LocalDate.now().plusDays(4), isSpecial = true))
					newDays.add(Day(date = LocalDate.now().plusDays(6), iconResId = R.drawable.space_invader, colorHex = "#00FF00"))
					newDays.add(Day(date = LocalDate.now().plusDays(8), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
					newDays.add(Day(date = LocalDate.now().minusDays(28), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
					newDays.add(Day(date = LocalDate.now().minusDays(29), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
					newDays.add(Day(date = LocalDate.now().minusDays(27), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))

					val startFrom = 1000L
					for (i in 0..999) {
						newDays.add(Day(date = LocalDate.now().plusDays(startFrom + i), iconResId = R.drawable.space_invader, colorHex = "#00FF00"))
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
