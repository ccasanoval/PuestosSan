package com.bancosantander.puestos.ui.views


import com.bancosantander.puestos.ui.viewModels.map.OwnWorkstationMapViewModel
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

////////////////////////////////////////////////////////////////////////////////////////////////////
object OwnWorkstationMapViewContract {

    interface View: BaseMvpView {
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init(model: OwnWorkstationMapViewModel)
    }
}