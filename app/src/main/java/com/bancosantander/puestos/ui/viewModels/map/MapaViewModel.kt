package com.bancosantander.puestos.ui.viewModels.map

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.graphics.PointF
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.data.firebase.fire.Fire
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.util.Plane
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.util.firebase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Math.abs
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*


////////////////////////////////////////////////////////////////////////////////////////////////////
class MapaViewModel(app: Application) : AndroidViewModel(app) {
	private val auth: Auth = getApplication<App>().auth
	private val fire: Fire = getApplication<App>().fire

	val mensaje = MutableLiveData<String>()
	//val usuario = MutableLiveData<String>()
	val puestos = MutableLiveData<List<Workstation>>()
	val selected = MutableLiveData<Workstation>()
	val camino = MutableLiveData<Array<PointF>>()
	val ini = MutableLiveData<PointF>()
	val end = MutableLiveData<PointF>()
	val ini100 = PointF()
	val end100 = PointF()
	val plane = Plane(getApplication())

	var fecha : Date = Date()

	init {
		puestos.value = listOf()
		getPuestosRT()
	}

	fun punto(pto: PointF, pto100: PointF) {
		if(!info(pto, pto100))
			ruta(pto, pto100)
	}

	//______________________________________________________________________________________________
	fun getPuestosRT() {

		WorkstationFire.getFreeWithDateRT(fire, fecha.firebase(), { lista, error ->
		//WorkstationFire.getAllRT(fire, { lista, error ->
			if(error == null) {
				puestos.value = lista.toList()
				Log.e(TAG, "getPuestosRT:------------------------------------------------------"+lista.size)
			}
			else {
				Log.e(TAG, "getPuestosRT:e:------------------------------------------------------",error)
				mensaje.value = getApplication<App>().getString(R.string.puestos_get_error)
			}
		})
	}


	//______________________________________________________________________________________________
	private fun info(pto: PointF, pto100: PointF): Boolean {
		Log.e(TAG, "info: ------------ info: PTO="+pto+" -- "+pto100+" -- "+puestos.value)

		if(puestos.value != null && puestos.value!!.isNotEmpty()) {
			return infoHelper(puestos.value!!, pto, pto100)
		}
		else {
			return false
		}
	}
	//______________________________________________________________________________________________
	private fun infoHelper(puestos: List<Workstation>, pto: PointF, pto100: PointF): Boolean {
		Log.e(TAG, "infoHelper: ------------ info: PTO="+pto+" -- "+pto100+" -- ")

		val MAX = 2f
		val candidatos = puestos.filter { puesto ->
			abs(puesto.x - pto100.x) < MAX && abs(puesto.y - pto100.y) < MAX
		}
		var seleccionado: Workstation? = null
		var minDistancia = 2*MAX
		for(puesto in candidatos) {
			Log.e(TAG, "BUSCANDO: ------------ info: PTO="+puesto)
			val dis = abs(puesto.x - pto100.x) + abs(puesto.y - pto100.y)
			if(minDistancia > dis) {
				minDistancia = dis
				seleccionado = puesto
				Log.e(TAG, "ENCONTRADO------------------------- info: PTO="+puesto.name+" : "+puesto)
			}
		}
		selected.value = seleccionado
		return (seleccionado != null)
	}

	//______________________________________________________________________________________________
	fun onStart() {
		EventBus.getDefault().register(this)
	}
	//______________________________________________________________________________________________
	fun onStop() {
		EventBus.getDefault().unregister(this)
	}
	//______________________________________________________________________________________________
	class RutaEvent(val ptoIni100: PointF, val ptoEnd100: PointF)
	@Subscribe
	fun calcRutaEventBus(event: RutaEvent) {
		calcRuta(event.ptoIni100, event.ptoEnd100)
	}
	//______________________________________________________________________________________________
	private fun calcRuta(ptoIni100: PointF, ptoEnd100: PointF) {
		ini100.set(ptoIni100)
		end100.set(ptoEnd100)
		Observable.defer({
			Observable.just(plane.calcRuta(ini100, end100))
		})
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({it ->
				camino.value = it.data
				if(it.data == null) {
					mensaje.value = getApplication<App>().getString(R.string.error_camino)
				}
				Log.e(TAG, "punto:calc-ruta: ok="+it.isOk+", pasosBusqueda="+it.pasosBusqueda+", pasos="+it.pasos)
			})
	}
	//______________________________________________________________________________________________
	private fun ruta(pto: PointF, pto100: PointF) {
		camino.value = null
		if(pto100.x < 0 || pto100.x >= 100 || pto100.y < 0 || pto100.y >= 100) {
			mensaje.value = getApplication<App>().getString(R.string.error_outside_bounds)
			Log.e(TAG, "punto:e:out of boundaries--------------"+pto+" : "+pto100+" :: "+ini.value+" : "+end.value)
			return
		}

		ini100.set(Plane.entrada)
		end100.set(pto100)
		end.value = pto

		/// En otro hilo
		Observable.defer({
			Observable.just(plane.calcRuta(ini100, end100))
			})
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ it ->
				camino.value = it.data
				if(it.data == null) {
					mensaje.value = getApplication<App>().getString(R.string.error_camino)
				}
				Log.e(TAG, "punto:calc-ruta: ok="+it.isOk+", pasosBusqueda="+it.pasosBusqueda+", pasos="+it.pasos)
			})

	}
	companion object {
		private val TAG: String = MapaViewModel::class.java.simpleName
	}
}