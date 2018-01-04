package com.bancosantander.puestos.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.graphics.PointF
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.viewModels.map.MapaViewModel
import com.bancosantander.puestos.util.Plane
import org.greenrobot.eventbus.EventBus

/**
 * Created by ccasanova on 18/12/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
class PuestoViewModel(app: Application) : AndroidViewModel(app) {

	val mensaje = MutableLiveData<String>()
	var puesto: Workstation? = null

	fun entrada2puesto() {
		if(puesto == null) return
		val ptoIni100 = Plane.entrada
		val ptoEnd100 = PointF(puesto!!.x, puesto!!.y)
		EventBus.getDefault().post(MapaViewModel.RutaEvent(ptoIni100, ptoEnd100))
	}

	companion object {
		private val TAG: String = PuestoViewModel::class.java.simpleName
	}
}