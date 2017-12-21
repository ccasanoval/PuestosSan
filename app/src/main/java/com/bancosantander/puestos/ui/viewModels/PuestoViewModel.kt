package com.bancosantander.puestos.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.bancosantander.puestos.data.models.Workstation

/**
 * Created by ccasanova on 18/12/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
class PuestoViewModel(app: Application) : AndroidViewModel(app) {

	val mensaje = MutableLiveData<String>()
	var puesto: Workstation? = null

	companion object {
		private val TAG: String = PuestoViewModel::class.java.simpleName
	}
}