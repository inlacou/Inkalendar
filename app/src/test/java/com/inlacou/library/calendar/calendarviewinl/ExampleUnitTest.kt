package com.inlacou.library.calendar.calendarviewinl

import com.inlacou.library.calendar.calendarviewinl.calendar.day.DayViewMdl
import com.inlacou.library.calendar.calendarviewinl.calendar.immediatePreviousMonth
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

	@Test
	fun calendar_immediateBefore() {
		val november0 = Calendar.getInstance()
		november0.set(Calendar.YEAR, 2017)
		november0.set(Calendar.MONTH, 10)
		val december0 = Calendar.getInstance()
		december0.set(Calendar.YEAR, 2017)
		december0.set(Calendar.MONTH, 11)
		val january1 = Calendar.getInstance()
		january1.set(Calendar.YEAR, 2018)
		january1.set(Calendar.MONTH, 0)
		val february1 = Calendar.getInstance()
		february1.set(Calendar.YEAR, 2018)
		february1.set(Calendar.MONTH, 1)
		val november1 = Calendar.getInstance()
		november1.set(Calendar.YEAR, 2018)
		november1.set(Calendar.MONTH, 10)
		val december1 = Calendar.getInstance()
		december1.set(Calendar.YEAR, 2018)
		december1.set(Calendar.MONTH, 11)

		//Yep
		assert(january1.immediatePreviousMonth(february1))
		assert(november0.immediatePreviousMonth(december0))
		assert(november1.immediatePreviousMonth(december1))
		assert(december0.immediatePreviousMonth(january1))

		//Nope
		assert(!january1.immediatePreviousMonth(january1))
		assert(!february1.immediatePreviousMonth(january1))
		assert(!february1.immediatePreviousMonth(november1))
		assert(!february1.immediatePreviousMonth(november0))
		assert(!december1.immediatePreviousMonth(january1))
	}
}
