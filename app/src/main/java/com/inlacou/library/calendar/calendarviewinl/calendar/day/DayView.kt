package com.inlacou.library.calendar.calendarviewinl.calendar.day

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.*
import com.inlacou.library.calendar.calendarviewinl.calendar.utils.ImageUtils

import kotlinx.android.synthetic.main.view_day.view.*

class DayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: FrameLayout(context, attrs, defStyleAttr) {

	var surfaceLayout: View? = null
	var tvDay: TextView? = null
	var ivDay: ImageView? = null

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
		ivDay = iv_day
	}

	fun initialize(view: View) {
		controller = DayViewCtrl(view = this, model = model)
	}

	fun populate() {
		//Set to normal
		tvDay?.text = model.model.calendar.timeInMillis.toDay(context)
		ImageUtils.loadResource(context, ivDay, model.iconResId)
		tvDay?.setTextColor(ContextCompat.getColor(context, model.textNormalColorResId))

		//Icon check
		ivDay.setVisible(model.iconResId!=null, true)

		//Selected check
		if(model.model.isSelected){
			model.selectedBackColorResId?.let { tvDay?.setBackgroundResource(it) }
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textSelectedColorResId))
		}

		//Special check
		if(model.model.isSpecial){
			//TODO model.specialTextBackColorResId?.let { textDayBack?.setBackgroundResource(it) }
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textSpecialColorResId))
		}

		//Current month check
		if(!model.isCurrentMonth){
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textDisabledOtherMonthColorResId))
			ivDay?.alpha = 0.12f
		}
	}

	private fun setListeners() {
		surfaceLayout?.setOnClickListener { controller.onClick() }
	}
}