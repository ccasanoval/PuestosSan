package com.bancosantander.puestos.ui.presenters

import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

/**
 * Created by mbalduciel on 14/12/17.
 */

class MainPresenter: BasePresenter<MainViewContract.View>(), MainViewContract.Presenter {

    override fun init() {


    }

    fun goToManageOwnWorkstation() {
        router().goToManageOwnWorkstation()
    }

    fun goToFillWorkstation() {
        router().goToFillWorkstation()
    }

    fun logout() {
        auth().logout()
    }
}