package com.bancosantander.puestos.ui.presenters

import android.os.Bundle
import android.util.Log
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

/**
 * Created by mbalduciel on 14/12/17.
 */

class MainPresenter: BasePresenter<MainViewContract.View>(), MainViewContract.Presenter {

    override fun init() {
        mView?.showLoading()
        auth().getUserFire { user, throwable ->
            when(user.type){
                User.Type.Fixed -> {
                    mView?.disableWorkstationList()
                }
                User.Type.Interim -> {
                    mView?.enableWorkstationList()
                }
                User.Type.Admin -> {

                }
            }
            mView?.hideLoading()
        }
    }
    override fun getEmail(): String {
        return auth().getEmail() ?: "Undefined"
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

    fun gotToConfiguration() {
        router().goToConfiguration()
    }

    fun callToChangePassword(oldPass: String, newPass: String) {
        if (oldPass.length < 6 || newPass.length < 6) mView?.showErrorMinLenght()
        else{
            mView?.showLoading()
            auth().changePassword(oldPass,newPass,{ status, error ->
                mView?.hideLoading()
                if (status){
                    mView?.showSuccess()
                    mView?.showTutorial()
                } else {
                    mView?.showError("Error al cambiar la contraseña")
                    mView?.showTutorial()
                    Log.e("CallToChangePassword", error.toString())
                }
            })
        }
    }

    fun goToTutorial(){
        router().goToTutorial()
    }
}