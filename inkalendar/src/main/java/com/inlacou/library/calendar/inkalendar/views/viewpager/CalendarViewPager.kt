package com.inlacou.library.calendar.inkalendar.views.viewpager

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View

/**
 * Created by Mateusz Kornakiewicz on 21.11.2017.
 * Forked by Inlacou on 26.04.2018.
 */
class CalendarViewPager
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
	: ViewPager(context, attrs) {

	//This method is needed to get wrap_content height for ViewPager
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		var finalHeight = heightMeasureSpec
		var auxHeight = 0

		for (i in 0 until childCount) {
			val child = getChildAt(i)
			child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))

			val h = child.measuredHeight

			if (h > auxHeight) {
				auxHeight = h
			}
		}

		if (auxHeight != 0) {
			finalHeight = View.MeasureSpec.makeMeasureSpec(auxHeight, View.MeasureSpec.EXACTLY)
		}

		super.onMeasure(widthMeasureSpec, finalHeight)
	}
}