package com.bancosantander.puestos.ui.presenters.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.Snackbar
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.viewModels.listWorkstation.WorkstationsListViewModel
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewFragmentContract
import com.bancosantander.puestos.ui.views.map.WorkstationsMapViewFragmentContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import org.jetbrains.anko.contentView

class WorkstationsMapFragmentPresenter(val context: WorkstationsActivity) : BasePresenter<WorkstationsMapViewFragmentContract.View>(), WorkstationsMapViewFragmentContract.Presenter {


    override fun init() {

    }

}