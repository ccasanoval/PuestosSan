package com.bancosantander.puestos.ui.presenters.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PointF
import android.support.design.widget.Snackbar
import android.view.MotionEvent

import android.widget.Toast
import com.bancosantander.puestos.R
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.viewModels.listWorkstation.WorkstationsListViewModel
import com.bancosantander.puestos.ui.viewModels.map.MapaViewModel
import com.bancosantander.puestos.ui.views.map.WorkstationsMapViewFragmentContract
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.firebase
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import java.util.*

class WorkstationsMapFragmentPresenter(val context: WorkstationsActivity) : BasePresenter<WorkstationsMapViewFragmentContract.View>(), WorkstationsMapViewFragmentContract.Presenter {


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
    fun setData(date: Date = Date()) {
        mView?.showLoading()
        model.fecha = date
        WorkstationFire.getFreeWithDateRT(fire(), date.firebase(), { workstationList, error ->
            mView?.hideLoading()
            if(error == null) {
                model.puestos.value = workstationList.toList()
                com.bancosantander.puestos.util.Log.e("MapaViewModel", "getPuestosRT:------------------------------------------------------"+workstationList.size)
            }
            else {
                model.puestos.value = listOf()
                com.bancosantander.puestos.util.Log.e("MapaViewModel", "getPuestosRT:e:------------------------------------------------------",error)
                model.mensaje.value = context.getString(R.string.puestos_get_error)
            }
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