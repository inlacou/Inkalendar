package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
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
    onClick: (InkDayMdl) -> Unit
) {
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
            .clip(CircleShape)
            .clickable(enabled = dayMdl.model.isEnabled) { // Enable click for all months
                onClick(dayMdl)
            },
        contentAlignment = Alignment.Center
    ) {
        if (dayMdl.isSelected && dayMdl.selectedBackResId != null) {
            Surface(
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = if (dayMdl.isCurrentMonth) 0.2f else 0.1f),
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
