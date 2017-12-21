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

    override fun callToChangePassword(oldPass:String, pass: String) {
        if (oldPass.length < 6 || pass.length < 6) mView?.showErrorMinLenght()
        else{
            mView?.showLoading()
            auth().changePassword(oldPass,pass,{ status, error ->
                mView?.hideLoading()
                if (status) mView?.showSuccess() else mView?.showError("Error al cambiar la contraseña")
                Log.e("CallToChangePassword",error.toString())
            })
        }

    }
}