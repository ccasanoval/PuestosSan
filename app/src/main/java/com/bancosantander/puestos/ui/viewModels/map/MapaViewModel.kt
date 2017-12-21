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
import com.bancosantander.puestos.data.firebase.fire.UserFire
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.util.Plane
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.data.models.Workstation
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Math.abs

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

	/*enum class Modo { Ruta, Info, Puestos, Anadir, Borrar }
	var modo = Modo.Ruta
		set(value) {
			field = value
			if(modo == Modo.Puestos) {
				getPuestosRT()
			}
		}*/

	init {
		puestos.value = listOf()
		//val fire = Fire()
		/*if(auth.getEmail() != null)
		UserFire.get(fire, auth.getEmail().toString(), { user: User, error ->
			if(error == null) {
				usuario.value = user.name +" : "+user.type
			}
			else {
				usuario.value = auth.getEmail()
				Log.e(TAG, "init:userFire.get:e:---------------------------------------------"+auth.getEmail().toString(), error)
			}
		})*/
		getPuestosRT()
	}

	//fun logout() { auth.logout() }

	fun punto(pto: PointF, pto100: PointF) {
		/*when(modo) {
			Modo.Ruta -> ruta(pto, pto100)
			Modo.Info -> info(pto, pto100)
			else -> info(pto, pto100)
		}*/
		if(!info(pto, pto100))
			ruta(pto, pto100)
	}

	//______________________________________________________________________________________________
	private fun getPuestosRT() {
		WorkstationFire.getAllRT(fire, { lista, error ->
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
		if(puestos.value != null && puestos.value!!.isNotEmpty()) {
			return infoHelper(puestos.value!!, pto, pto100)
		}
		else {
			return false
		}
	}
	//______________________________________________________________________________________________
	private fun infoHelper(puestos: List<Workstation>, pto: PointF, pto100: PointF): Boolean {
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
	private fun ruta(pto: PointF, pto100: PointF) {
		camino.value = null
		if(pto100.x < 0 || pto100.x >= 100 || pto100.y < 0 || pto100.y >= 100) {
			mensaje.value = getApplication<App>().getString(R.string.error_outside_bounds)
			Log.e(TAG, "punto:e:out of boundaries--------------"+pto+" : "+pto100+" :: "+ini.value+" : "+end.value)
			return
		}
		if(ini.value == null || end.value != null) {
			ini100.set(pto100)
			ini.value = pto
			end.value = null
		}
		else {
			end100.set(pto100)
			end.value = pto

			/// En otro hilo

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
	}
	companion object {
		private val TAG: String = MapaViewModel::class.java.simpleName
	}
}