package com.bancosantander.puestos.ui.views

import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

/**
 * Created by mbalduciel on 16/12/17.
 */

object SearchUserViewContract {

    interface View: BaseMvpView {
        fun showLoading()
        fun hideLoading()
        fun showSuccess()
        fun showError(s: String)
    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init()
    }
}