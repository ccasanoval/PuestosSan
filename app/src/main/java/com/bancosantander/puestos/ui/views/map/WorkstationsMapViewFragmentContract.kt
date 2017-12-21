package com.bancosantander.puestos.ui.views.map

import com.bancosantander.puestos.data.models.Workstation
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragmentView
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

/**
 * Created by mbalduciel on 16/12/17.
 */

object WorkstationsMapViewFragmentContract {

    interface View: BaseMvpView {

    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init()
    }
}