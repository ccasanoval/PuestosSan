package com.bancosantander.puestos.ui.presenters

import android.util.Log
import com.bancosantander.puestos.data.firebase.fire.UserFire
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

/**
 * Created by mbalduciel on 14/12/17.
 */

class MainPresenter: BasePresenter<MainViewContract.View>(), MainViewContract.Presenter {

    var hadCahngedPass : Boolean = false
    lateinit var userName : String
    lateinit var user: User
    override fun init() {
        mView?.showLoading()
        auth().getUserFire { user, throwable ->
            this.user = user
            when(user.type){
                User.Type.Fixed -> {
                    mView?.showWorkstationInMap()
                }
                User.Type.Interim -> {
                    mView?.enableWorkstationList()
                }
                User.Type.Admin -> {

                }
            }
            hadCahngedPass = user.hadChangedPass
            userName = user.name
            mView?.hideLoading()
            mView?.hadChangedPass()
        }
    }

    override fun getEmail(): String {
        return auth().getEmail() ?: "Undefined"
    }

    fun goToManageOwnWorkstation() {
        router().goToManageOwnWorkstation()
    }

    fun goToFillWorkstation() {
        if(user.type.equals(User.Type.Interim)){
            router().goToFillWorkstation()
        }else{
            router().goToTutorial()
        }

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
                if (status){
                    UserFire.updateHadChangedPass(fire(),userName,{user,error->
                        if(error != null){
                            mView?.hideLoading()
                            mView?.showTutorial()
                        }else{
                            Log.e("CallToChangePassword", error.toString())
                            mView?.hideLoading()
                            mView?.getMyActivity()?.finish()
                        }
                    })
                } else {
                    mView?.hideLoading()
                    mView?.getMyActivity()?.finish()
                    Log.e("CallToChangePassword", error.toString())
                }
            })
        }
    }

    fun goToTutorial(){
        router().goToTutorial()
    }

    override fun goToSearchUser() {
        router().gotoSearchUser()
    }

    override fun hadChangedPass(): Boolean {
        return hadCahngedPass
    }
}