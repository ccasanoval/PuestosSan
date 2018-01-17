package com.bancosantander.puestos.ui.presenters.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PointF

import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.fire.Fire
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.CommonArea
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationMapActivity
import com.bancosantander.puestos.ui.viewModels.map.MapaViewModel
import com.bancosantander.puestos.ui.views.map.OwnWorkstationsMapViewFragmentContract
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.firebase
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import java.util.*

class OwnWorkstationsMapFragmentPresenter(val context: OwnWorkstationMapActivity) : BasePresenter<OwnWorkstationsMapViewFragmentContract.View>(), OwnWorkstationsMapViewFragmentContract.Presenter {


    lateinit var model: MapaViewModel
    override fun init() {
        mView?.let {
            model = ViewModelProviders.of(context).get(MapaViewModel::class.java)
            model.mensaje.observe(context, Observer { mensaje ->
            })
            model.camino.observe(context, Observer<Array<PointF>> { camino ->
                if(camino == null)	it.delCamino()
                else				it.showCamino(camino)
            })
			model.commons.observe(context, Observer<List<CommonArea>> { commons ->
				it.showCommons(commons!!)
			})
            model.puestos.observe(context, Observer<List<Workstation>> { puestos ->
                //Log.e(TAG, "------------------------------- PUESTOS OBSERVER")
                when {
                    puestos == null ->{
                        it.showPuestos(listOf())
                        Log.e("Map fragment presenter","workstation null")
                    }
                    puestos.isEmpty() -> {
                        it.showPuestos(listOf())
                        Log.e("Map fragment presenter","workstation list empty")
                    }
                    else ->
                        it.showPuestos(puestos)
                }
            })
            model.selected.observe(context, Observer<Workstation>{ pto -> it.showSeleccionado(pto) })
            model.ini.observe(context, Observer<PointF>{ pto -> it.showPointF(true,pto) })
            model.end.observe(context, Observer<PointF>{ pto -> it.showPointF(false,pto) })
        }

    }
    fun setData() {
        mView?.showLoading()

		model.camino.value = null
		WorkstationFire.getCommonAreas(fire(), {lista, error ->
			model.commons.value = lista
			mView?.hideLoading()
		})

		WorkstationFire.getWorkstation(fire(), auth().getEmail()!!, { theworkstation, error ->
			model.puestos.value = List(1, {it->theworkstation})
			mView?.hideLoading()
		})
    }
     override fun setPoint(pto: PointF,pto100: PointF) {
        model.punto(pto, pto100)
    }
    override fun onStart() {
        model.onStart()
    }

    override fun onStop() {
        model.onStop()
    }

}