package com.inlacou.library.calendar.inkalendar.views.day

import android.content.Context
import androidx.core.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.inlacou.library.calendar.inkalendar.R
import com.inlacou.library.calendar.inkalendar.setVisibleInk
import com.inlacou.library.calendar.inkalendar.toDay
import com.inlacou.library.calendar.inkalendar.utils.ImageUtils

import kotlinx.android.synthetic.main.view_day.view.*

class DayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: FrameLayout(context, attrs, defStyleAttr) {

	private var surfaceLayout: View? = null
	private var tvDay: TextView? = null
	private var ivDay: ImageView? = null
	private var ivSelected: ImageView? = null

	var model: DayViewMdl = DayViewMdl()
		set(value) {
			field = value
			controller.model = value
			setListeners()
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
		ivSelected = iv_selected
	}

	private fun initialize(view: View) {
		controller = DayViewCtrl(view = this, model = model)
	}

	private fun populate() {
		//Set to normal
		tvDay?.text = model.model.calendar.timeInMillis.toDay(context)
		tvDay?.alpha = 1f
		ivDay?.alpha = 1f
		ivSelected?.alpha = 1f
		ivSelected?.setBackgroundResource(0)
		ImageUtils.loadResource(context, ivDay, model.model.iconResId)
		tvDay?.setTextColor(ContextCompat.getColor(context, model.textNormalColorResId))

		//Icon check
		ivDay.setVisibleInk(model.model.iconResId!=null, true)

		//Selected check
		if(model.isSelected){
			model.selectedBackResId?.let { ivSelected?.setBackgroundResource(it) }
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textSelectedColorResId))
		}

		//Special check
		if(model.model.isSpecial){
			model.specialTextBackColorResId?.let { tvDay?.setBackgroundResource(it) }
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textSpecialColorResId))
		}

		//Disabled check
		if(!model.model.isEnabled){
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textDisabledColorResId))
			surfaceLayout?.setOnClickListener {  }
		}

		//Current month check
		if(!model.isCurrentMonth){
			tvDay?.setTextColor(ContextCompat.getColor(context, model.textDisabledOtherMonthColorResId))
			tvDay?.alpha = 0.12f //TODO change this alpha modifying with more colors
			ivDay?.alpha = 0.12f
			ivSelected?.alpha = 0.12f
			surfaceLayout?.setOnClickListener {  }
		}
	}

	private fun setListeners() {
		surfaceLayout?.setOnClickListener { controller.onClick() }
	}
}