package com.bancosantander.puestos.ui.views

import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView
import java.util.*

/**
 * Created by mbalduciel on 16/12/17.
 */

object OwnWorkstationViewContract {

    interface View: BaseMvpView {
        fun showWorkstation(idOwner: String, idUser: String, status: Workstation.Status,number: String)
        fun showBtnLiberar()
        fun showBtnOcupar()
        fun hideButtons()
        fun showMyDialog(no_tiene_puesto_ocupado: Int)
        fun showLoading()
        fun hideLoading()
        fun configMenuFixed()
        fun configMenuInterim()
    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init(model: OwnWorkstationViewModel)
        fun fillWorkstation(date:String)
        fun releaseMyWorkstation(date: String)
        fun showCurrentWorkstation(date: Date = Date())
    }
}