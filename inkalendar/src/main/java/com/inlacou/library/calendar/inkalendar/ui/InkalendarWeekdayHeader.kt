package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inlacou.library.calendar.inkalendar.R

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
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InkalendarWeekdayHeaderPreview() {
    MaterialTheme {
        InkalendarWeekdayHeader()
    }
}
