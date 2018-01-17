package com.bancosantander.puestos.ui.presenters

import com.bancosantander.puestos.ui.viewModels.map.OwnWorkstationMapViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationMapViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

////////////////////////////////////////////////////////////////////////////////////////////////////
class OwnWorkstationMapPresenter : BasePresenter<OwnWorkstationMapViewContract.View>(), OwnWorkstationMapViewContract.Presenter {
    lateinit var model: OwnWorkstationMapViewModel
    override fun init(model:OwnWorkstationMapViewModel) {
        this.model = model
        subscribeModel()
    }

    private fun subscribeModel() {

    }
}