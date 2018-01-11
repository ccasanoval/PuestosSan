package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.Observer
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.bancosantander.puestos.util.firebase
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import kotlinx.coroutines.experimental.async
import java.text.SimpleDateFormat
import java.util.*


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

     override fun showCurrentWorkstation(date: Date){
        mView?.showLoading()
        auth().getUserFire { user, throwable ->
            when(user.type){
                User.Type.Fixed -> {
                    auth().getEmail()?.let {
                        WorkstationFire.getWorkstationRTV2(mView?.getMyActivity()!!,fire(),it,User.IdType.idOwner.name,date.firebase(),{ workstation, error ->
                            mView?.configMenuFixed()
                            retrieveWorkstation(workstation,error)
                        })
                    }
                }
                User.Type.Interim -> {
                    auth().getEmail()?.let{
                        WorkstationFire.getWorkstationRTV2(mView?.getMyActivity()!!,fire(),it,User.IdType.idUser.name,date.firebase(),{ workstation, error ->
                            mView?.configMenuInterim()
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
                mView?.noWorkstationFill()
            }
            else {
                model.currentWorkstation?.value = workstation
                owner = workstation.idOwner
                when(workstation.status){
                    Workstation.Status.Occupied -> mView?.showBtnLiberar()
                    Workstation.Status.Free -> mView?.showBtnOcupar()
                    else -> mView?.hideButtons()
                }
                mView?.workstationFill()
            }
        }
    }
    override fun releaseMyWorkstation(date: Date) {
        mView?.showLoading()
        auth().getEmail()?.let {
            async {
                WorkstationFire.releaseMyWorkstationV2(fire(),owner,date.firebase(),{workstation, error ->
                    showCurrentWorkstation(date)
                })
            }
        }
    }

    override fun fillWorkstation(date: Date) {
        mView?.showLoading()
       auth().getEmail()?.let{
           async {
               WorkstationFire.fillWorkstationV2(fire(), it, it,date.firebase(), { workstation, error ->
                   retrieveWorkstation(workstation,error)
               })
           }
       }
    }

    override fun goToWorkstationList() {
        router().goToFillWorkstation()
        mView?.getMyActivity()?.finish()
    }
}