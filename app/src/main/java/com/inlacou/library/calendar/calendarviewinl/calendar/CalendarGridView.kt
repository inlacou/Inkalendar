package com.inlacou.library.calendar.calendarviewinl.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.GridView

/**
 * Created by Mateusz Kornakiewicz on 15.11.2017.
 * Forked by Inlacou on 26.04.2018.
 */
class CalendarGridView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: GridView(context, attrs, defStyleAttr) {

	//This method is needed to get wrap_content height for GridView
	public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE shr 2,
				View.MeasureSpec.AT_MOST)
		super.onMeasure(widthMeasureSpec, expandSpec)
	}
}