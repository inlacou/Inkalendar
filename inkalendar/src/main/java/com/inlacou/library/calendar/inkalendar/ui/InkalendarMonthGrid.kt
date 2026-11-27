package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate

@Composable
fun InkalendarMonthGrid(
    position: Int,
    model: InkalendarMdl,
    colors: InkalendarColors = InkalendarDefaults.colors(),
    onDayClick: (LocalDate) -> Unit
) {
    val days by remember(position, model) {
        derivedStateOf {
            computeDays(position, model)
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(count = days.size) { index: Int ->
            val dayMdl = days[index]
            InkalendarDay(
                dayMdl = dayMdl,
                colors = colors,
                onClick = { onDayClick(dayMdl.model.date) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InkalendarMonthGridPreview() {
    MaterialTheme {
        InkalendarMonthGrid(
            position = 0,
            model = InkalendarMdl(today = LocalDate.now()),
            onDayClick = {}
        )
    }
}
