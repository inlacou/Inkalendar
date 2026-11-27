package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * A number of months (pages) in the calendar
 * 2401 months means 1200 months (100 years) before and 1200 months (100 years) after the today month
 */
const val INKALENDAR_SIZE = 2401
const val FIRST_VISIBLE_PAGE = INKALENDAR_SIZE / 2

@Composable
fun Inkalendar(
    modifier: Modifier = Modifier,
    model: InkalendarMdl,
    colors: InkalendarColors = InkalendarDefaults.colors(),
    onDayClick: (LocalDate) -> Unit = {},
    onForward: (LocalDate) -> Unit = {},
    onBackward: (LocalDate) -> Unit = {},
    onPageLoad: (Pair<LocalDate, LocalDate>) -> Unit = {},
) {
    val initialPage = FIRST_VISIBLE_PAGE + model.initialMonth

    val pagerState = rememberPagerState(
        initialPage = initialPage,
        pageCount = { INKALENDAR_SIZE }
    )
    val coroutineScope = rememberCoroutineScope()

    var lastPage by remember { mutableIntStateOf(initialPage) }

    val currentMonthDate = remember(pagerState.currentPage, model.today) {
        model.today.plusMonths((pagerState.currentPage - FIRST_VISIBLE_PAGE).toLong())
    }

    LaunchedEffect(pagerState.currentPage) {
        val page = pagerState.currentPage
        if (page > lastPage) {
            onForward(currentMonthDate)
        } else if (page < lastPage) {
            onBackward(currentMonthDate)
        }
        lastPage = page

        val days = computeDays(page - FIRST_VISIBLE_PAGE, model)
        if (days.isNotEmpty()) {
            onPageLoad(Pair(days.first().model.date, days.last().model.date))
        }
    }

    Column(modifier = modifier) {
        InkalendarHeader(
            date = currentMonthDate,
            onPreviousClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            },
            onNextClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        )

        InkalendarWeekdayHeader()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            InkalendarMonthGrid(
                position = page - FIRST_VISIBLE_PAGE,
                model = model,
                colors = colors,
                onDayClick = onDayClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InkalendarPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Inkalendar(
                model = InkalendarMdl(
                    today = LocalDate.now(),
                    selectedDays = listOf(LocalDate.now())
                )
            )
        }
    }
}
