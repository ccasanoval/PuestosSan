package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.viewModels.listWorkstation.WorkstationsListViewModel
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

class WorkstationsPresenter(val context: WorkstationsActivity) : BasePresenter<WorkstationsViewContract.View>(), WorkstationsViewContract.Presenter {

    lateinit var model : WorkstationsListViewModel

    override fun init() {
        model = ViewModelProviders.of(context).get(WorkstationsListViewModel::class.java)
        model.getWorkstationsList().observe(context, Observer { workstationList ->
            workstationList?.let {
                mView?.setDataAdapter(it)
            }
        })
    }
    fun setData(){
        WorkstationFire.getAllRT(fire(),{workstationList, error ->
            if (error != null) {

            }else {
                model.workstationsList?.value = workstationList
            }
        })
    }

}