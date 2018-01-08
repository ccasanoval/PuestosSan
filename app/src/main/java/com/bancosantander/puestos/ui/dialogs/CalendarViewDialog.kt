package com.bancosantander.puestos.ui.dialogs


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_SINGLE
import kotlinx.android.synthetic.main.calendar_view_layout.*
import java.util.*


class CalendarViewDialog : DialogFragment() {

	companion object{
		private var INSTANCE : CalendarViewDialog?=null
		lateinit var callback : (date: List<Date>?)->Unit
		lateinit var date : Date
		private var multipleSelection : Boolean = false
		fun getInstance(multipleSelection:Boolean,date:Date,callback:(date: List<Date>?)->Unit):CalendarViewDialog {
			this.date = date
			this.multipleSelection = multipleSelection
			this.callback = callback
			return INSTANCE ?: CalendarViewDialog()
		}
	}

	override fun onResume() {
		super.onResume()
		val width = getResources().getDisplayMetrics().widthPixels - 100
		val height = resources.getDimensionPixelSize(R.dimen.calendar_dialog_height)
		dialog.window!!.setLayout(width, height)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		super.onCreateView(inflater, container, savedInstanceState)
		return inflater?.inflate(R.layout.calendar_view_layout,container,false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		when(multipleSelection){
            true -> calendarView.selectionMode = SELECTION_MODE_MULTIPLE
            false -> calendarView.selectionMode = SELECTION_MODE_SINGLE
        }
		calendarView.setSelectedDate(date)
		calendarView.setOnDateChangedListener { widget, date, selected ->
            if(!multipleSelection){
                    dismiss()
                    callback(calendarView.selectedDates.map{it.date})
            }
		}
	}
}