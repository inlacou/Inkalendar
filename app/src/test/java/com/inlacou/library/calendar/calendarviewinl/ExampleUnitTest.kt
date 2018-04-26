package com.inlacou.library.calendar.calendarviewinl

import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayViewMdl
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	@Test
	fun addition_isCorrect() {
		assertEquals(4, 2 + 2)
	}

	@Test
	fun dayViewModel_equality() {
		val now = Calendar.getInstance()
		val now1 = DayViewMdl(now.time)
		val now2 = DayViewMdl(now.time)
		val tomorrow = Calendar.getInstance()
		tomorrow.add(Calendar.DAY_OF_YEAR, 1)
		val tomorrow1 = DayViewMdl(tomorrow.time)
		val tomorrow2 = DayViewMdl(tomorrow.time)

		assert(now1==now2)
		assert(tomorrow1==tomorrow2)
		assert(now1!=tomorrow2)
	}
}
