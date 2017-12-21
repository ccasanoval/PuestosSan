package com.bancosantander.puestos.ui.views

import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

/**
 * Created by mbalduciel on 16/12/17.
 */

object ConfigurationViewContract {

    interface View: BaseMvpView {
        fun changePassword()
        fun showErrorMinLenght()
        fun showSuccess()
        fun showError(error: String)
        fun showLoading()
        fun hideLoading()
    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init()
        fun callToChangePassword(oldPass: String, pass:String)
        fun changePassword()
    }
}