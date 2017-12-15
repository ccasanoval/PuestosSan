package com.cesoft.puestos.ui.mapa

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.graphics.PointF
import com.cesoft.puestos.App
import com.cesoft.puestos.util.Log
import com.cesoft.puestos.R
import com.cesoft.puestos.data.auth.Auth
import com.cesoft.puestos.data.fire.Fire
import com.cesoft.puestos.data.fire.UserFire
import com.cesoft.puestos.data.fire.WorkstationFire
import com.cesoft.puestos.util.Plane
import com.cesoft.puestos.models.User
import com.cesoft.puestos.models.Workstation
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.math.abs

/**
 * Created by ccasanova on 29/11/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
class MapaViewModel(app: Application) : AndroidViewModel(app) {
	private val auth: Auth = getApplication<App>().auth
	private val fire: Fire = getApplication<App>().fire

	val mensaje = MutableLiveData<String>()
	val usuario = MutableLiveData<String>()//<List<Boolean>>? = null
	val puestos = MutableLiveData<List<Workstation>>()
	val puestos100 = arrayListOf<Workstation>()
	val camino = MutableLiveData<Array<PointF>>()
	val ini = MutableLiveData<PointF>()
	val end = MutableLiveData<PointF>()
	val ini100 = PointF()
	val end100 = PointF()
	val plane = Plane(getApplication())

	enum class Modo { Ruta, Info, Puestos, Anadir, Borrar }
	var modo = Modo.Ruta
		set(value) {
			field = value
			if(modo == Modo.Puestos) {
				getPuestosRT()
			}
		}

	//______________________________________________________________________________________________
	init {
		puestos.value = listOf()
		//val fire = Fire()
		if(auth.getEmail() != null)
		UserFire.get(fire, auth.getEmail().toString(), { user: User, error ->
			if(error == null) {
				usuario.value = user.name +" : "+user.type
			}
			else {
				usuario.value = auth.getEmail()
				Log.e(TAG, "init:userFire.get:e:---------------------------------------------"+auth.getEmail().toString(), error)
			}
		})
	}

	//______________________________________________________________________________________________
	fun logout() { auth.logout() }

	//______________________________________________________________________________________________
	fun punto(pto: PointF, pto100: PointF) {

		when(modo) {
			Modo.Ruta -> ruta(pto, pto100)
			Modo.Info -> info(pto, pto100)
			else -> info(pto, pto100)
				//Log.e(TAG, "punto: IGNORADO------------------------------------------------")
		}
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
	private fun getPuestos(callback: (List<Workstation>) -> Unit = {}) {
		WorkstationFire.getAll(fire, { lista, error ->
			if(error == null) {
				puestos.value = lista.toList()
				callback(lista.toList())
				Log.e(TAG, "getPuestos:------------------------------------------------------"+lista.size)
			}
			else {
				Log.e(TAG, "getPuestos:e:------------------------------------------------------",error)
				mensaje.value = getApplication<App>().getString(R.string.puestos_get_error)
			}
		})
	}

	//______________________________________________________________________________________________
	private fun info(pto: PointF, pto100: PointF) {
		Log.e(TAG, "info:----------------------------"+pto100+" / "+pto)
		//TODO: Mostrar pantalla que permite eliminar, modificar o crear puesto: Dependiendo de user y type user
		//TODO: Buscar workstation cercania: Aun no hay soporte en Firestore para consultas por radio de GeoPoints
		if(puestos.value != null && puestos.value!!.isNotEmpty()) {
			infoHelper(puestos.value!!, pto, pto100)
		}
		else {
			getPuestos({ lospuestos -> infoHelper(lospuestos, pto, pto100) })
		}
	}
	//______________________________________________________________________________________________
	private fun infoHelper(puestos: List<Workstation>, pto: PointF, pto100: PointF) {
		for(puesto in puestos) {
			Log.e(TAG, "BUSCANDO: ------------ info: PTO="+puesto)
			if( abs(puesto.x - pto100.x) < 3 && abs(puesto.y - pto100.y) < 3) {
				Log.e(TAG, "ENCONTRADO------------------------- info: PTO="+puesto.name+" : "+puesto)
				if(this.puestos.value != null && this.puestos.value!!.isEmpty()) {
					//this.modo = Modo.Puestos
					this.puestos.value = arrayListOf(puesto)
				}
				break
			}
		}
	}

	//______________________________________________________________________________________________
	private fun ruta(pto: PointF, pto100: PointF) {
		//Log.e(TAG, "punto:-----0-----------"+pto100+" :: "+ini.value+" : "+end.value)
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

	//______________________________________________________________________________________________
	companion object {
		private val TAG: String = MapaViewModel::class.java.simpleName
	}
}