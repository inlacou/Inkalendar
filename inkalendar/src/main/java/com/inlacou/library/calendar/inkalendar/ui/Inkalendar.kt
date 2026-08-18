package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.business.Day
import com.inlacou.library.calendar.inkalendar.toDay
import com.inlacou.library.calendar.inkalendar.toMonthYear
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.core.graphics.toColorInt

/**
 * A number of months (pages) in the calendar
 * 2401 months means 1200 months (100 years) before and 1200 months (100 years) after the today month
 */
const val INKALENDAR_SIZE = 2401

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Inkalendar(
    modifier: Modifier = Modifier,
    model: InkalendarMdl,
    onDayClick: (LocalDate) -> Unit = {},
    onPageLoad: (Pair<LocalDate, LocalDate>) -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = InkalendarMdl.FIRST_VISIBLE_PAGE) {
        INKALENDAR_SIZE
    }
    val coroutineScope = rememberCoroutineScope()

    val currentMonthDate = remember(pagerState.currentPage, model.today) {
        model.today.plusMonths((pagerState.currentPage - InkalendarMdl.FIRST_VISIBLE_PAGE).toLong())
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

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            InkalendarMonthGrid(
                position = page - InkalendarMdl.FIRST_VISIBLE_PAGE,
                model = model,
                onDayClick = onDayClick,
                onPageLoad = onPageLoad
            )
        }
    }
}

@Composable
fun InkalendarHeader(
    date: LocalDate,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous")
        }
        Text(
            text = date.toMonthYear(context),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        IconButton(onClick = onNextClick) {
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next")
        }
    }
}

@Composable
fun InkalendarMonthGrid(
    position: Int,
    model: InkalendarMdl,
    onDayClick: (LocalDate) -> Unit,
    onPageLoad: (Pair<LocalDate, LocalDate>) -> Unit
) {
    val days by remember(position, model.today) {
        derivedStateOf {
            computeDays(position, model)
        }
    }

    LaunchedEffect(days) {
        if (days.isNotEmpty()) {
            onPageLoad(Pair(days.first().model.date, days.last().model.date))
        }
    }

    Column {
        InkalendarWeekdayHeader()
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 400.dp)
        ) {
            items(count = days.size) { index: Int ->
                val dayMdl = days[index]
                InkalendarDay(
                    dayMdl = dayMdl,
                    onClick = { onDayClick(dayMdl.model.date) }
                )
            }
        }
    }
}

@Composable
fun InkalendarWeekdayHeader() {
    val weekdays = listOf(
        R.string.inkalendar_monday,
        R.string.inkalendar_tuesday,
        R.string.inkalendar_wednesday,
        R.string.inkalendar_thursday,
        R.string.inkalendar_friday,
        R.string.inkalendar_saturday,
        R.string.inkalendar_sunday
    )
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        weekdays.forEach { resId ->
            Text(
                text = stringResource(id = resId),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun InkalendarDay(
    dayMdl: InkDayMdl,
    onClick: (InkDayMdl) -> Unit
) {
    val context = LocalContext.current
    val alpha = if (dayMdl.isCurrentMonth) 1f else 0.3f // Increased alpha for better visibility
    
    val textColorRes = when {
        !dayMdl.model.isEnabled -> dayMdl.textDisabledColorResId
        dayMdl.isSelected -> dayMdl.textSelectedColorResId
        dayMdl.model.isSpecial -> dayMdl.textSpecialColorResId
        !dayMdl.isCurrentMonth -> dayMdl.textDisabledOtherMonthColorResId
        else -> dayMdl.textNormalColorResId
    }
    val textColor = colorResource(id = textColorRes)

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = dayMdl.model.isEnabled) { // Enable click for all months
                onClick(dayMdl)
            },
        contentAlignment = Alignment.Center
    ) {
        if (dayMdl.isSelected && dayMdl.selectedBackResId != null) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colors.primary.copy(alpha = if (dayMdl.isCurrentMonth) 0.2f else 0.1f),
                modifier = Modifier.fillMaxSize(0.9f)
            ) {}
        }
        if (dayMdl.model.isSpecial && dayMdl.specialTextBackColorResId != null) {
            Surface(
                shape = CircleShape,
                color = colorResource(id = dayMdl.specialTextBackColorResId).copy(alpha = alpha * 0.5f),
                modifier = Modifier.fillMaxSize(0.7f)
            ) {}
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = dayMdl.model.date.dayOfMonth.toString(),
                color = textColor.copy(alpha = alpha),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = if (dayMdl.isSelected) FontWeight.Bold else FontWeight.Normal
            )
            
            if (dayMdl.model.iconResId != null) {
                val tint = when {
                    dayMdl.model.colorResId != null -> colorResource(id = dayMdl.model.colorResId)
                    dayMdl.model.colorHex != null -> Color(dayMdl.model.colorHex.toColorInt())
                    else -> LocalContentColor.current
                }
                Icon(
                    painter = painterResource(id = dayMdl.model.iconResId),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp), // Adjusted size
                    tint = tint.copy(alpha = alpha)
                )
            }
        }
    }
}

private fun computeDays(position: Int, calendarModel: InkalendarMdl): List<InkDayMdl> {
    val days = mutableListOf<InkDayMdl>()
    val monthDate = calendarModel.today.plusMonths(position.toLong()).withDayOfMonth(1)
    
    val firstDayOfMonth = monthDate.withDayOfMonth(1)
    val dayOfWeek = firstDayOfMonth.dayOfWeek.value // Monday=1, ..., Sunday=7
    val daysFromPreviousMonth = dayOfWeek - 1
    
    var startDate = firstDayOfMonth.minusDays(daysFromPreviousMonth.toLong())
    
    val filteredDays = calendarModel.days.filter { it.date.isAfter(startDate.minusDays(1)) && it.date.isBefore(startDate.plusDays(43)) }

    fun addDay(date: LocalDate) {
        val dayInl = filteredDays.find { it.date == date }
        val isCurrentMonth = date.month == monthDate.month
        
        val dayMdl = InkDayMdl(dayInl ?: Day(date))
        dayMdl.isSelected = calendarModel.selectedDays.any { it == date }
        dayMdl.isCurrentMonth = isCurrentMonth
        days.add(dayMdl)
    }

    for (i in 0 until 42) {
        addDay(startDate)
        startDate = startDate.plusDays(1)
    }
    
    return days
}
