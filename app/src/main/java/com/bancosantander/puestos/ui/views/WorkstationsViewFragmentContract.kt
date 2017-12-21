package com.bancosantander.puestos.ui.views

import com.bancosantander.puestos.data.models.Workstation
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragmentView
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

/**
 * Created by mbalduciel on 16/12/17.
 */

object WorkstationsViewFragmentContract {

    interface View: BaseMvpView {
        fun setDataAdapter(list: ArrayList<Workstation>)
        fun showLoading()
        fun hideLoading()

    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init()
    }
}