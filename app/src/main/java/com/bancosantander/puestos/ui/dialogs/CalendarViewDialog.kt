package com.bancosantander.puestos.ui.dialogs


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.bancosantander.puestos.util.firebase
import kotlinx.android.synthetic.main.calendar_view_layout.*
import java.util.*


class CalendarViewDialog : DialogFragment() {

	companion object{
		private var INSTANCE : CalendarViewDialog?=null
		lateinit var callback : (date:Date)->Unit
		fun getInstance(callback:(date: Date)->Unit):CalendarViewDialog {
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
		calendarView.setOnDateChangedListener { widget, date, selected ->
			dismiss()
			callback(date.date)
		}
	}
}