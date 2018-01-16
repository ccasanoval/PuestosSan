package com.bancosantander.puestos.ui.presenters

import com.bancosantander.puestos.data.firebase.fire.UserFire
import com.bancosantander.puestos.ui.views.SearchUserViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

/**
 * Created by mbalduciel on 14/12/17.
 */

class SearchUserPresenter : BasePresenter<SearchUserViewContract.View>(), SearchUserViewContract.Presenter {


    override fun init() {

    }

    fun getUsers(){
        mView?.showLoading()
        UserFire.getAll(fire(),{usersList, throwable ->
            if(throwable == null){
                mView?.setMyAdapter(usersList.toMutableList())
            }else{

            }
            mView?.hideLoading()
    })
}

}