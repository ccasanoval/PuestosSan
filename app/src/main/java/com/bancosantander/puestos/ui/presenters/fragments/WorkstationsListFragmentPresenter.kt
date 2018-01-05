package com.bancosantander.puestos.ui.presenters.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.viewModels.listWorkstation.WorkstationsListViewModel
import com.bancosantander.puestos.ui.views.WorkstationsViewFragmentContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import kotlinx.coroutines.experimental.async

class WorkstationsListFragmentPresenter(val context: WorkstationsActivity) : BasePresenter<WorkstationsViewFragmentContract.View>(), WorkstationsViewFragmentContract.Presenter {

    lateinit var model: WorkstationsListViewModel

    override fun init() {
        model = ViewModelProviders.of(context).get(WorkstationsListViewModel::class.java)
        model.getWorkstationsList().observe(context, Observer { workstationList ->
            workstationList?.let {
                mView?.setDataAdapter(it)
            }
        })
    }

    fun setData() {
        mView?.showLoading()
        WorkstationFire.getFreeWithDateRT(fire(),"040118", { workstationList, error ->
            mView?.hideLoading()
            if (error != null) {

            } else {
                model.workstationsList?.value = workstationList
            }
        })
        /*WorkstationFire.getAllRT(fire(), { workstationList, error ->
            mView?.hideLoading()
            if (error != null) {

            } else {
                model.workstationsList?.value = workstationList
            }
        })*/
    }

    fun fillWorkstation(idOwner: String, date: String) {
        auth().getEmail()?.let {
            mView?.showLoading()
            async {
                WorkstationFire.fillWorkstationV2(fire(), idOwner, it,date, { workstation, error ->
                    mView?.hideLoading()
                    if (error != null) {

                    } else {
                        mView?.getMyActivity()?.showInfoScreenDialog(R.string.filled_workstation)
                    }
                })
            }

        }

    }

    fun checkIfUserHaveWorkstation(idOwner: String, date: String) {
        auth().getEmail()?.let{
            WorkstationFire.getWorkstationRT(mView?.getMyActivity()!!,fire(),it, User.IdType.idUser.name,{ workstation, error ->
                if(workstation == null && error == null){
                    fillWorkstation(idOwner,date)
                }
                else{
                    if(workstation != null && !workstation.idOwner.equals(idOwner)){
                        mView?.showDialog(R.string.liberar_puesto)
                    }
                }
            })
        }
    }
}