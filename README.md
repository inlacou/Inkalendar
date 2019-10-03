# Inkalendar
[![](https://jitpack.io/v/inlacou/Inkalendar.svg)](https://jitpack.io/#inlacou/Inkalendar)

Better Calendar View for Android

## Usage

In your layout.xml:

```XML
	<com.inlacou.library.calendar.inkalendar.views.calendar.Inkalendar
		android:id="@+id/calendarView"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"/>
```

In your activity (or fragment, or whatever):

```Kt

val days = mutableListOf<DayInl>(
        DayInl(calendar = Calendar.getInstance().addDays(1), isEnabled = false) //Disabled
				, DayInl(calendar = Calendar.getInstance().addDays(2), isEnabled = false) //Disabled
				, DayInl(calendar = Calendar.getInstance().addDays(3), isEnabled = true) //Enabled
				, DayInl(calendar = Calendar.getInstance().addDays(4), isSpecial = true) //Special
				, DayInl(calendar = Calendar.getInstance().addDays(6), iconResId = R.drawable.space_invader) //Menacing space invader as icon
				, DayInl(calendar = Calendar.getInstance().addDays(8), isSpecial = true, iconResId = R.drawable.space_invader) //Special day when menacing space invader attacks
)
calendarView.model = InkalendarMdl(
				today = (Calendar.getInstance().clone() as Calendar) //Set starting day (default to *today*)
				, mode = InkalendarMdl.Mode.SINGLE_SELECTION,
				days = days, //We set days defined before "special days", or days with something (being disabled, having an icon, or something)
				selectedDays = mutableListOf(
						Calendar.getInstance()
				), //Current day selected
				singleDaySelection = {
					Toast.makeText(this, "selected ${it.dayOfMonth}, ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
				},
				onBackward = {
					Toast.makeText(this, "onBackward ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
				},
				onForward = {
					Toast.makeText(this, "onForward ${it.month} of ${it.year}", Toast.LENGTH_SHORT).show()
				},
				onPageLoad = {
					Toast.makeText(this,
							"newPage from ${it.first.dayOfMonth}/${it.first.month}/${it.first.year} " +
									"to ${it.second.dayOfMonth}/${it.second.month}/${it.second.year}",
							Toast.LENGTH_SHORT).show()
				})
```

## UI Configuration

You can override color resources to style the calendar:

```XML
	<color name="inkalendar_back_top_bar_color">#55F</color>
	<color name="inkalendar_text_day_name_color">#000</color>
	<color name="inkalendar_text_current_date_color">#fff</color>
	<color name="inkalendar_text_normal_color">#1d1d26</color>
	<color name="inkalendar_text_special_color">#ffffff</color>
	<color name="inkalendar_back_disabled_color">#FF0C23</color>
	<color name="inkalendar_text_selected_color">#ffffff</color>
	<color name="inkalendar_text_disabled_color">#FF0C23</color>
	<color name="inkalendar_text_disabled_other_month_color">#8D8D91</color>
```

You can override String resources to style calendar and provide translations:

```XML
	<string name="inkalendar_monday">M</string>
	<string name="inkalendar_tuesday">T</string>
	<string name="inkalendar_wednesday">W</string>
	<string name="inkalendar_thursday">T</string>
	<string name="inkalendar_friday">F</string>
	<string name="inkalendar_saturday">S</string>
	<string name="inkalendar_sunday">S</string>
	
	<array name="inkalendar_months_array">
		<item>January</item>
		<item>February</item>
		<item>March</item>
		<item>April</item>
		<item>May</item>
		<item>June</item>
		<item>July</item>
		<item>August</item>
		<item>September</item>
		<item>October</item>
		<item>November</item>
		<item>December</item>
	</array>
	
	<string name="inkalendar_day">%1$td</string>
	<string name="inkalendar_month_year">%1$tm %1$tY</string>
	<string name="inkalendar_month_as_text_year">%1$tb. %1$tY</string>
```
