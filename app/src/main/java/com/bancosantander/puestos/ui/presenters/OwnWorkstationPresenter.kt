package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.Observer
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter


class OwnWorkstationPresenter: BasePresenter<OwnWorkstationViewContract.View>(), OwnWorkstationViewContract.Presenter {
    lateinit var model:OwnWorkstationViewModel
    lateinit var owner:String
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
        mView?.showLoading()
        auth().getUserFire { user, throwable ->
            when(user.type){
                User.Type.Fixed -> {
                    auth().getEmail()?.let {
                        WorkstationFire.getWorkstationRT(mView?.getMyActivity()!!,fire(),it,User.IdType.idOwner.name,{ workstation, error ->
                            retrieveWorkstation(workstation,error)
                        })
                    }
                }
                User.Type.Interim -> {
                    auth().getEmail()?.let{
                        WorkstationFire.getWorkstationRT(mView?.getMyActivity()!!,fire(),it,User.IdType.idUser.name,{ workstation, error ->
                            retrieveWorkstation(workstation,error)
                        })
                    }
                }
                User.Type.Admin -> {
                    auth().getEmail()?.let{

                    }
                }
            }
        }
    }

    private fun retrieveWorkstation(workstation: Workstation?,error: Throwable?) {
        mView?.hideLoading()
        if (error != null) {

        } else {
            if(workstation == null) {
                model.currentWorkstation?.value = Workstation()
                mView?.hideButtons()
                mView?.getMyActivity()?.showInfoScreenDialog(R.string.no_tiene_puesto_ocupado)
            }
            else {
                model.currentWorkstation?.value = workstation
                owner = workstation.idOwner
                when(workstation.status){
                    Workstation.Status.Occupied -> mView?.showBtnLiberar()
                    Workstation.Status.Free -> mView?.showBtnOcupar()
                }
            }
        }
    }
    fun releaseMyWorkstation() {
        mView?.showLoading()
        auth().getEmail()?.let {
            WorkstationFire.releaseMyWorkstation(fire(),owner,{workstation, error ->
                retrieveWorkstation(workstation,error)
            })
        }
    }

    override fun fillWorkstation() {
        mView?.showLoading()
       auth().getEmail()?.let{
           WorkstationFire.fillWorkstation(fire(), it, it, { workstation, error ->
               mView?.hideLoading()
               if (error != null) {

               } else {

               }
           })
       }
    }
}