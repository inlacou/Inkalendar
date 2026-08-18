package com.inlacou.library.calendar.calendarviewinl

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.inlacou.inker.Inker
import com.inlacou.library.calendar.inkalendar.business.Day
import com.inlacou.library.calendar.inkalendar.ui.Inkalendar
import com.inlacou.library.calendar.inkalendar.ui.InkalendarMdl
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.time.Duration.Companion.milliseconds

class MainActivity : AppCompatActivity() {

	@SuppressLint("CheckResult")
	@OptIn(ExperimentalMaterial3Api::class)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		Inker.mix(DebugColor())
		Inker.d { "INKER - ---------------------------------------------------" }

		setContent {
			MaterialTheme {
				var model by remember {
					mutableStateOf(
						InkalendarMdl(
							today = LocalDate.now(),
							mode = InkalendarMdl.Mode.SINGLE_SELECTION,
							selectedDays = listOf(LocalDate.now()),
						)
					)
				}

				Scaffold(
					topBar = {
						CenterAlignedTopAppBar(
							title = { Text("Inkalendar") },
							colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
								containerColor = MaterialTheme.colorScheme.primary,
								titleContentColor = MaterialTheme.colorScheme.onPrimary
							),
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
								model = model.copy(selectedDays = listOf(day))
								Toast.makeText(this, "selected ${day.dayOfMonth}, ${day.monthValue} of ${day.year}", Toast.LENGTH_SHORT).show()
							} else {
								val newSelectedDays = if (model.selectedDays.contains(day)) {
									model.selectedDays.filter { it != day }
								} else {
									model.selectedDays + day
								}
								model = model.copy(selectedDays = newSelectedDays)
							}
						},
						onForward = { date ->
							Toast.makeText(this, "onForward ${date.monthValue} of ${date.year}", Toast.LENGTH_SHORT).show()
						},
						onBackward = { date ->
							Toast.makeText(this, "onBackward ${date.monthValue} of ${date.year}", Toast.LENGTH_SHORT).show()
						},
						onPageLoad = { fromTo ->
							Toast.makeText(this,
								"newPage from ${fromTo.first.dayOfMonth}/${fromTo.first.monthValue}/${fromTo.first.year} " +
										"to ${fromTo.second.dayOfMonth}/${fromTo.second.monthValue}/${fromTo.second.year}",
								Toast.LENGTH_SHORT).show()
						},
						onPageChanged = { newPage ->
							model = model.copy(currentPage = newPage)
						}
					)
				}

				LaunchedEffect(Unit) {
					//Load data "asynchronously"
					delay(3000.milliseconds)
					val ts = System.currentTimeMillis()
					val newDays = mutableListOf<Day>()
					newDays.add(Day(date = LocalDate.now().plusDays(1), isEnabled = false))
					newDays.add(Day(date = LocalDate.now().plusDays(2), isEnabled = false))
					newDays.add(Day(date = LocalDate.now().plusDays(3), isEnabled = true))
					newDays.add(Day(date = LocalDate.now().plusDays(4), isSpecial = true))
					newDays.add(Day(date = LocalDate.now().plusDays(6), iconResId = R.drawable.space_invader, colorHex = "#00FF00"))
					newDays.add(Day(date = LocalDate.now().plusDays(8), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
					newDays.add(Day(date = LocalDate.now().minusDays(28), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
					newDays.add(Day(date = LocalDate.now().minusDays(29), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))
					newDays.add(Day(date = LocalDate.now().minusDays(27), isSpecial = true, iconResId = R.drawable.space_invader, colorResId = R.color.yellow))

					model = model.copy(days = model.days + newDays)

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
