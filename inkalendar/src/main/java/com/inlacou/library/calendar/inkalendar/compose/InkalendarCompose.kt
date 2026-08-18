package com.inlacou.library.calendar.inkalendar.compose

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
import com.inlacou.library.calendar.inkalendar.business.DayInl
import com.inlacou.library.calendar.inkalendar.month
import com.inlacou.library.calendar.inkalendar.sameYearAndMonth
import com.inlacou.library.calendar.inkalendar.toDay
import com.inlacou.library.calendar.inkalendar.toMidnight
import com.inlacou.library.calendar.inkalendar.toMonthYear
import com.inlacou.library.calendar.inkalendar.views.calendar.InkalendarMdl
import com.inlacou.library.calendar.inkalendar.views.day.DayViewMdl
import kotlinx.coroutines.launch
import java.util.*
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
    onDayClick: (Calendar) -> Unit = {},
    onPageLoad: (Pair<Calendar, Calendar>) -> Unit = {}
) {
    val pagerState = rememberPagerState(initialPage = InkalendarMdl.FIRST_VISIBLE_PAGE) {
        INKALENDAR_SIZE
    }
    val coroutineScope = rememberCoroutineScope()

    val currentMonthCalendar = remember(pagerState.currentPage, model.today) {
        val cal = model.today.clone() as Calendar
        cal.add(Calendar.MONTH, pagerState.currentPage - InkalendarMdl.FIRST_VISIBLE_PAGE)
        cal
    }

    Column(modifier = modifier) {
        InkalendarHeader(
            calendar = currentMonthCalendar,
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
    calendar: Calendar,
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
            text = calendar.timeInMillis.toMonthYear(context),
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
    onDayClick: (Calendar) -> Unit,
    onPageLoad: (Pair<Calendar, Calendar>) -> Unit
) {
    val days = remember(position, model.days, model.selectedDays, model.today) {
        computeDays(position, model)
    }

    LaunchedEffect(days) {
        if (days.isNotEmpty()) {
            onPageLoad(Pair(days.first().model.calendar, days.last().model.calendar))
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
                    onClick = { onDayClick(dayMdl.model.calendar) }
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
    dayMdl: DayViewMdl,
    onClick: (DayViewMdl) -> Unit
) {
    val context = LocalContext.current
    val alpha = if (dayMdl.isCurrentMonth) 1f else 0.12f
    
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
            .clickable(enabled = dayMdl.model.isEnabled && dayMdl.isCurrentMonth) {
                onClick(dayMdl)
            },
        contentAlignment = Alignment.Center
    ) {
        if (dayMdl.isSelected && dayMdl.selectedBackResId != null) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
                modifier = Modifier.fillMaxSize(0.8f)
            ) {}
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = dayMdl.model.calendar.timeInMillis.toDay(context),
                color = textColor.copy(alpha = alpha),
                textAlign = TextAlign.Center,
                fontSize = 14.sp
            )
            
            dayMdl.model.iconResId?.let { iconRes ->
                val tint = when {
                    dayMdl.model.colorResId != null -> colorResource(id = dayMdl.model.colorResId)
                    dayMdl.model.colorHex != null -> Color(dayMdl.model.colorHex.toColorInt())
                    else -> null
                }
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp).alpha(alpha),
                    tint = tint ?: LocalContentColor.current
                )
            }
        }
    }
}

private fun computeDays(position: Int, calendarModel: InkalendarMdl): List<DayViewMdl> {
    val days = mutableListOf<DayViewMdl>()
    val calendar = (calendarModel.today.clone() as Calendar).toMidnight()
    calendar.add(Calendar.MONTH, -InkalendarMdl.FIRST_VISIBLE_PAGE)
    calendar.add(Calendar.MONTH, position)

    val startingCal = calendar.clone() as Calendar
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val monthBeginningCell = dayOfWeek + if (dayOfWeek == 1) 5 else -2
    calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell)
    
    val from = calendar.clone() as Calendar

    fun filter(item: Calendar, start: Calendar): Boolean = 
        item.timeInMillis > start.timeInMillis && item.timeInMillis < start.timeInMillis + 3628800000L
    
    val filteredDays = calendarModel.days.filter { filter(it.calendar, from) }

    fun addDay(days: MutableList<DayViewMdl>, calendar: Calendar, modelDays: List<DayInl>) {
        val newCal = calendar.clone() as Calendar
        val dayInl = modelDays.find { calendar.toMidnight() == it.calendar.toMidnight() }
        val isCurrentMonth = calendar.month == startingCal.month
        
        val dayMdl = DayViewMdl(dayInl ?: DayInl(newCal))
        dayMdl.isSelected = calendarModel.selectedDays.any { it.toMidnight() == calendar.toMidnight() }
        dayMdl.isCurrentMonth = isCurrentMonth
        days.add(dayMdl)
    }

    fun addUntil(number: Int, days: MutableList<DayViewMdl>, calendar: Calendar, modelDays: List<DayInl>) {
        while (days.size < number) {
            addDay(days, calendar, modelDays)
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    addUntil(27, days, calendar, filteredDays)
    do {
        addDay(days, calendar, filteredDays)
        calendar.add(Calendar.DAY_OF_MONTH, 1)
    } while (calendar.sameYearAndMonth(startingCal))

    addUntil(42, days, calendar, filteredDays)
    
    return days
}
