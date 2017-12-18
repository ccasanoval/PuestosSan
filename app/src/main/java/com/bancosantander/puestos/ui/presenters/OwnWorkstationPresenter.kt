package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.router.Router
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.bancosantander.puestos.ui.views.MainViewContract
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.bancosantander.puestos.util.app
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter


class OwnWorkstationPresenter(val context: OwnWorkstationActivity) : BasePresenter<OwnWorkstationViewContract.View>(), OwnWorkstationViewContract.Presenter {
    lateinit var model:OwnWorkstationViewModel
    override fun init() {
        model = ViewModelProviders.of(context).get(OwnWorkstationViewModel::class.java)
        showCurrentWorkstation()
    }
    fun showCurrentWorkstation(){
        if (model.getCurrentWorkstation() == null){
            auth().getEmail()?.let {
                WorkstationFire.getWorkstation(fire(),it,{workstation, error ->
                    retrieveWorkstation(workstation,error)
                })
            }
        }else {
            model.getCurrentWorkstation()?.observe(context, Observer { workstation ->
                workstation?.let {
                    with(workstation){
                        val nameOwner = idOwner.split('@')[0]
                        mView?.showWorkstation(nameOwner,idUser,status,name)
                    }
                }
            })
        }

    }

    private fun retrieveWorkstation(workstation: Workstation,error: Throwable?) {
        if (error != null) {

        } else {
            model.setCurrentWorkstation(workstation)
            showCurrentWorkstation()
        }
    }

    fun releaseMyWorkstation() {
        auth().getEmail()?.let {
            WorkstationFire.releaseMyWorkstation(fire(),it,{workstation, error ->
                retrieveWorkstation(workstation,error)
            })
        }
    }

}