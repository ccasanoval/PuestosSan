package com.bancosantander.puestos.ui.presenters

import android.arch.lifecycle.ViewModelProviders
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.viewModels.WorkstationsListViewModel
import com.bancosantander.puestos.ui.views.MainViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter
import java.security.AccessControlContext

/**
 * Created by mbalduciel on 14/12/17.
 */

class WorkstationsPresenter(val context: WorkstationsActivity) : BasePresenter<WorkstationsViewContract.View>(), WorkstationsViewContract.Presenter {

    lateinit var model :WorkstationsListViewModel

    override fun init() {
        model = ViewModelProviders.of(context).get(WorkstationsListViewModel::class.java)
    }

}