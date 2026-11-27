package com.inlacou.library.calendar.calendarviewinl

import com.inlacou.library.calendar.inkalendar.business.Day
import com.inlacou.library.calendar.inkalendar.isMonthBefore
import com.inlacou.library.calendar.inkalendar.sameYearAndMonth
import com.inlacou.library.calendar.inkalendar.ui.InkDayMdl

import org.junit.Test

import org.junit.Assert.*
import java.time.LocalDate

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
	fun localDate_equality() {
		val one = LocalDate.of(2018, 4, 10)
		val two = LocalDate.of(2018, 4, 10)
		assert(one == two)
		assert(one.sameYearAndMonth(two))
	}

	@Test
	fun dayViewModel_equality() {
		val now = LocalDate.now()
		val now1 = InkDayMdl(Day(now))
		val now2 = InkDayMdl(Day(now))
		val tomorrow = LocalDate.now().plusDays(1)
		val tomorrow1 = InkDayMdl(Day(tomorrow))
		val tomorrow2 = InkDayMdl(Day(tomorrow))

		assert(now1 == now2)
		assert(tomorrow1 == tomorrow2)
		assert(now1 != tomorrow2)
	}

	@Test
	fun month_before() {
		val november0 = LocalDate.of(2017, 11, 1)
		val december0 = LocalDate.of(2017, 12, 1)
		val january1 = LocalDate.of(2018, 1, 1)
		val february1 = LocalDate.of(2018, 2, 1)

		assert(november0.isMonthBefore(december0))
		assert(december0.isMonthBefore(january1))
		assert(january1.isMonthBefore(february1))

		assert(!january1.isMonthBefore(january1))
		assert(!february1.isMonthBefore(january1))
	}
}
