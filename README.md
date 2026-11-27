# Inkalendar
[![](https://jitpack.io/v/inlacou/Inkalendar.svg)](https://jitpack.io/#inlacou/Inkalendar)

Better Calendar View for Android

## Usage

```kt
var model by remember {
    mutableStateOf(
        InkalendarMdl(
            today = LocalDate.now(),
            mode = InkalendarMdl.Mode.SINGLE_SELECTION,
            selectedDays = listOf(LocalDate.now()),
        )
    )
}

```

```Kt
Inkalendar(
    modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
    model = model,
    onDayClick = { day ->
        if (model.mode == InkalendarMdl.Mode.SINGLE_SELECTION) {
            model = model.copy(selectedDays = listOf(day))
            Toast.makeText(this, "selected ${day.dayOfMonth}, ${day.monthValue} of ${day.year}", Toast.LENGTH_SHORT).show()
        } else {
            val newSelectedDays = if (model.selectedDays.contains(day)) {
                model.selectedDays.filter { it != day }
            } else {
                model.selectedDays + day
            }
            model = model.copy(selectedDays = newSelectedDays)
        }
    },
    onForward = { date ->
        Toast.makeText(this, "onForward ${date.monthValue} of ${date.year}", Toast.LENGTH_SHORT).show()
    },
    onBackward = { date ->
        Toast.makeText(this, "onBackward ${date.monthValue} of ${date.year}", Toast.LENGTH_SHORT).show()
    },
    onPageLoad = { fromTo ->
        Toast.makeText(this,
            "newPage from ${fromTo.first.dayOfMonth}/${fromTo.first.monthValue}/${fromTo.first.year} " +
                    "to ${fromTo.second.dayOfMonth}/${fromTo.second.monthValue}/${fromTo.second.year}",
            Toast.LENGTH_SHORT).show()
    },
)
```

## UI Configuration

It will load colors from your theme, or you can change them passing a `InkalendarColors` data class instance to `Inkalendar`.