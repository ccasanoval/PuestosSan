package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.design.widget.Snackbar
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.viewModels.listWorkstation.WorkstationsListViewModel
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import org.jetbrains.anko.contentView

class WorkstationsPresenter : BasePresenter<WorkstationsViewContract.View>(), WorkstationsViewContract.Presenter {

    override fun init() {

    }

}