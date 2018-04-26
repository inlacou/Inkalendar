package com.inlacou.library.calendar.calendarviewinl.calendar.day

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.toDay

import kotlinx.android.synthetic.main.view_day.view.*
import java.util.*

class DayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: FrameLayout(context, attrs, defStyleAttr) {

	var surfaceLayout: View? = null
	var tvDay: TextView? = null

	var model: DayViewMdl = DayViewMdl()
		set(value) {
			field = value
			controller.model = value
			populate()
		}
	private lateinit var controller: DayViewCtrl

	init {
		this.initialize()
		setListeners()
		populate()
	}

	protected fun initialize() {
		val rootView = View.inflate(context, R.layout.view_day, this)
		initialize(rootView)
		surfaceLayout = view_base_layout_surface
		tvDay = tv_day

	}

	fun initialize(view: View) {
		controller = DayViewCtrl(view = this, model = model)
	}

	fun populate() {
		tvDay?.text = model.day.time.toDay(context)
	}

	private fun setListeners() {
		surfaceLayout?.setOnClickListener { controller.onClick() }
	}
}