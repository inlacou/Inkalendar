package com.inlacou.library.calendar.inkalendar.views.calendar

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.inlacou.library.calendar.inkalendar.*

import com.inlacou.library.calendar.inkalendar.adapters.CalendarPagerAdapter
import com.inlacou.library.calendar.inkalendar.views.viewpager.CalendarViewPager
import java.util.*

open class Inkalendar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
	: LinearLayout(context, attrs, defStyleAttr) {

	private var surfaceLayout: View? = null
	private var tvCurrentMonth: TextView? = null
	private var forwardButton: ImageButton? = null
	private var previousButton: ImageButton? = null
	private var mViewPager: CalendarViewPager? = null

	var model: InkalendarMdl = InkalendarMdl()
		set(value) {
			field = value
			controller.model = value
			populate()
		}
	private lateinit var controller: InkalendarCtrl

	init {
		this.initialize()
		//TODO setAttributes(attrs)
		setListeners()
		populate()
	}

	private fun initialize() {
		val rootView = View.inflate(context, R.layout.view_inkcalendar, this)
		initialize(rootView)
		surfaceLayout = findViewById(R.id.view_base_layout_surface)
		tvCurrentMonth = findViewById(R.id.currentDateLabel)
		forwardButton = findViewById(R.id.btn_forward)
		previousButton = findViewById(R.id.btn_previous)
		mViewPager = findViewById(R.id.calendarViewPager)
	}

	private fun initialize(view: View) {
		controller = InkalendarCtrl(view = this, model = model)
	}

	private fun populate() {
		model.today = (model.today.clone() as Calendar).toMidnight()!!
		// This line subtracts half of all calendar months to set the calendar in the correct position (in the middle)
		model.today.add(Calendar.MONTH, -InkalendarMdl.FIRST_VISIBLE_PAGE)
		// Now we can go ${InkalendarMdl.FIRST_VISIBLE_PAGE} forward and ${InkalendarMdl.FIRST_VISIBLE_PAGE} backwards

		mViewPager?.adapter = CalendarPagerAdapter(context, model, {
			controller.onDayClick(it)
		}, {
			controller.onPageLoad(it)
		})
		// This line move calendar to the middle page
		mViewPager?.currentItem = InkalendarMdl.FIRST_VISIBLE_PAGE
	}

	private fun setListeners() {
		surfaceLayout?.setOnClickListener { controller.onClick() }
		forwardButton?.setOnClickListener { v: View -> moveToNext() }
		previousButton?.setOnClickListener { v: View -> moveToPrevious() }
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

	/**
	 * @param [complete] [Boolean] mostly a complete recreation of the view, use at your own risk
	 */
	fun notifyDataSetChanged(complete: Boolean = false) {
		(mViewPager?.adapter as CalendarPagerAdapter).notifyDataSetChanged(complete)
	}
}