package com.inlacou.library.calendar.calendarviewinl.calendar.views.calendar

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import com.inlacou.library.calendar.calendarviewinl.R
import com.inlacou.library.calendar.calendarviewinl.calendar.*
import com.inlacou.library.calendar.calendarviewinl.calendar.adapters.CalendarPagerAdapter
import kotlinx.android.synthetic.main.view_calendar_inl.view.*
import java.util.*

class CalendarViewInl @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: LinearLayout(context, attrs, defStyleAttr) {

	private var surfaceLayout: View? = null
	private var tvCurrentMonth: TextView? = null
	private var forwardButton: ImageButton? = null
	private var previousButton: ImageButton? = null
	private var mViewPager: CalendarViewPager? = null

	var model: CalendarViewInlMdl = CalendarViewInlMdl()
		set(value) {
			field = value
			controller.model = value
			populate()
		}
	private lateinit var controller: CalendarViewInlCtrl

	init {
		this.initialize()
		//TODO setAttributes(attrs)
		setListeners()
		populate()
	}

	protected fun initialize() {
		val rootView = View.inflate(context, R.layout.view_calendar_inl, this)
		initialize(rootView)
		surfaceLayout = view_base_layout_surface
		tvCurrentMonth = currentDateLabel
		forwardButton = btn_forward
		previousButton = btn_previous
		mViewPager = calendarViewPager
	}

	fun initialize(view: View) {
		controller = CalendarViewInlCtrl(view = this, model = model)
	}

	fun populate() {
		// This line subtracts a half of all calendar months to set calendar
		// in the correct position (in the middle)
		model.today.add(Calendar.MONTH, -CalendarViewInlMdl.FIRST_VISIBLE_PAGE)

		mViewPager!!.adapter = CalendarPagerAdapter(context, model)
		// This line move calendar to the middle page
		mViewPager!!.currentItem = CalendarViewInlMdl.FIRST_VISIBLE_PAGE
	}

	private fun setListeners() {
		surfaceLayout?.setOnClickListener { controller.onClick() }
		forwardButton?.setOnClickListener({ v: View -> moveToNext() })
		previousButton?.setOnClickListener({ v: View -> moveToPrevious() })
		mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
			override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

			/**
			 * This method set calendar header label
			 *
			 * @param position Current ViewPager position
			 * @see ViewPager.OnPageChangeListener
			 */
			override fun onPageSelected(position: Int) {
				controller.onPageSelected(position)
			}

			override fun onPageScrollStateChanged(state: Int) {}
		})
	}

	fun moveToNext(actualPos: Int = model.currentPage) {
		mViewPager?.currentItem = actualPos + 1
	}

	fun moveToPrevious(actualPos: Int = model.currentPage) {
		mViewPager?.currentItem = actualPos - 1
	}

	internal fun setHeaderName(calendar: Calendar) {
		tvCurrentMonth?.text = calendar.timeInMillis.toMonthYear(context)
	}
}