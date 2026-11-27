package com.inlacou.library.calendar.inkalendar.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class InkalendarColors(
    val textNormalColor: Color,
    val textSelectedColor: Color,
    val textSpecialColor: Color,
    val textDisabledColor: Color,
    val textDisabledOtherMonthColor: Color,
    val selectedDayBackgroundColor: Color,
    val specialTextBackgroundColor: Color
)

object InkalendarDefaults {
    @Composable
    fun colors(
        textNormalColor: Color = MaterialTheme.colorScheme.onSurface,
        textSelectedColor: Color = MaterialTheme.colorScheme.primary,
        textSpecialColor: Color = MaterialTheme.colorScheme.tertiary,
        textDisabledColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        textDisabledOtherMonthColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
        selectedDayBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        specialTextBackgroundColor: Color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)
    ): InkalendarColors = InkalendarColors(
        textNormalColor = textNormalColor,
        textSelectedColor = textSelectedColor,
        textSpecialColor = textSpecialColor,
        textDisabledColor = textDisabledColor,
        textDisabledOtherMonthColor = textDisabledOtherMonthColor,
        selectedDayBackgroundColor = selectedDayBackgroundColor,
        specialTextBackgroundColor = specialTextBackgroundColor
    )
}
