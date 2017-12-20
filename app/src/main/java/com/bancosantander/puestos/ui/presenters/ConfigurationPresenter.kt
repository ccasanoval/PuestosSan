package com.bancosantander.puestos.ui.presenters

import android.util.Log
import com.bancosantander.puestos.ui.views.ConfigurationViewContract
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

/**
 * Created by mbalduciel on 14/12/17.
 */

class ConfigurationPresenter : BasePresenter<ConfigurationViewContract.View>(), ConfigurationViewContract.Presenter {


    override fun init() {


    }

    override fun changePassword() {
        mView?.changePassword()
    }

    override fun callToChangePassword(pass: String) {
        if (pass.length < 6) mView?.showErrorMinLenght()
        else auth().changePassword(pass,{ status, error ->
            if (status) mView?.showSuccess() else mView?.showError("Error al cambiar la contraseña")
            Log.e("CallToChangePassword",error.toString())
        })
    }
}