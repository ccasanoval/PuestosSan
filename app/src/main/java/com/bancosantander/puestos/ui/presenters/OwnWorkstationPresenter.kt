package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter


class OwnWorkstationPresenter: BasePresenter<OwnWorkstationViewContract.View>(), OwnWorkstationViewContract.Presenter {
    lateinit var model:OwnWorkstationViewModel
    override fun init(model:OwnWorkstationViewModel) {
        this.model = model
        subscribeModel()
        showCurrentWorkstation()
    }

    private fun subscribeModel() {
        model.getCurrentWorkstation()?.observe(mView?.getMyActivity() as OwnWorkstationActivity, Observer { workstation ->
            workstation?.let {
                with(it) {
                    val nameOwner = idOwner.split('@')[0]
                    mView?.showWorkstation(nameOwner, idUser, status, name)
                }
            }
        })
    }

    private fun showCurrentWorkstation(){
        auth().getEmail()?.let {
            WorkstationFire.getWorkstationRT(mView?.getMyActivity()!!,fire(),it,{ workstation, error ->
                retrieveWorkstation(workstation,error)
            })
        }
    }

    private fun retrieveWorkstation(workstation: Workstation?,error: Throwable?) {
        if (error != null) {

        } else {
            //TODO("Dialog no tienes puesto ocupado")
            if(workstation == null) mView?.getMyActivity()?.finish()
            else {
                model.currentWorkstation?.value = workstation
            }
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