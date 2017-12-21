package com.bancosantander.puestos.ui.views

import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

/**
 * Created by mbalduciel on 16/12/17.
 */

object OwnWorkstationViewContract {

    interface View: BaseMvpView {
        fun showWorkstation(idOwner: String, idUser: String, status: Workstation.Status,number: String)

    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init(model: OwnWorkstationViewModel)
    }
}