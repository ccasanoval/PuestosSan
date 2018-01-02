package com.bancosantander.puestos.ui.dialogs


import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import kotlinx.android.synthetic.main.info_layout.*
import javax.security.auth.callback.Callback


class InfoScreenDialog : DialogFragment() {

	companion object{
		private var INSTANCE : InfoScreenDialog?=null
		private var infoText:String? = ""
		fun getInstance(infoText :String = "") : InfoScreenDialog{
			this.infoText = infoText
			return INSTANCE ?: InfoScreenDialog()
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(DialogFragment.STYLE_NO_TITLE, R.style.InfoDialogTheme)
	}

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		super.onCreateView(inflater, container, savedInstanceState)
		return inflater?.inflate(R.layout.info_layout,container,false)
	}

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		tvInfo.text = infoText
		ivCloseInfo.setOnClickListener {
			dismiss()
			activity.finish()
		}
	}
}