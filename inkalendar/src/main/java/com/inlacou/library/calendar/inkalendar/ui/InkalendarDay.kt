package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toColorInt
import com.inlacou.library.calendar.inkalendar.business.Day
import java.time.LocalDate

@Composable
fun InkalendarDay(
    dayMdl: InkDayMdl,
    colors: InkalendarColors = InkalendarDefaults.colors(),
    onClick: (InkDayMdl) -> Unit
) {
    val alpha = if (dayMdl.isCurrentMonth) 1f else 0.3f // Increased alpha for better visibility
    
    val textColor = when {
        !dayMdl.model.isEnabled -> colors.textDisabledColor
        dayMdl.isSelected -> colors.textSelectedColor
        dayMdl.model.isSpecial -> colors.textSpecialColor
        !dayMdl.isCurrentMonth -> colors.textDisabledOtherMonthColor
        else -> colors.textNormalColor
    }

    Box(
        modifier = Modifier
            .aspectRatio(1.2f)
            .clickable(enabled = dayMdl.model.isEnabled) { // Enable click for all months
                onClick(dayMdl)
            },
        contentAlignment = Alignment.Center
    ) {
        if (dayMdl.isSelected) {
            Surface(
                shape = CircleShape,
                color = colors.selectedDayBackgroundColor.copy(alpha = if (dayMdl.isCurrentMonth) colors.selectedDayBackgroundColor.alpha else colors.selectedDayBackgroundColor.alpha * 0.5f),
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .aspectRatio(1f)
            ) {}
        }
        if (dayMdl.model.isSpecial) {
            Surface(
                shape = CircleShape,
                color = colors.specialTextBackgroundColor.copy(alpha = alpha * 0.5f),
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .aspectRatio(1f)
            ) {}
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                //.padding(top = 6.dp)
        ) {
            Text(
                text = dayMdl.model.date.dayOfMonth.toString(),
                color = textColor.copy(alpha = alpha),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
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

@Preview(showBackground = true)
@Composable
fun InkalendarDayPreview() {
    MaterialTheme {
        Box(modifier = Modifier.size(50.dp)) {
            InkalendarDay(
                dayMdl = InkDayMdl(
                    model = Day(date = LocalDate.now()),
                    isSelected = true,
                    isCurrentMonth = true
                ),
                onClick = {}
            )
        }
    }
}
