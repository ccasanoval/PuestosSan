package com.bancosantander.puestos.ui.presenters

import android.os.Bundle
import android.util.Log
import com.bancosantander.puestos.data.firebase.fire.UserFire
import com.bancosantander.puestos.data.firebase.fire.WorkstationFire
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.ui.views.MainViewContract
import com.bancosantander.puestos.ui.views.SearchUserViewContract
import com.mibaldi.viewmodelexamplemvp.base.BasePresenter

/**
 * Created by mbalduciel on 14/12/17.
 */

class SearchUserPresenter : BasePresenter<SearchUserViewContract.View>(), SearchUserViewContract.Presenter {


    override fun init() {

    }

    fun getUsers():List<String>{
        mView?.showLoading()
        var listUserString : List<String> = mutableListOf()
        UserFire.getAll(fire(),{usersList, throwable ->
            if(throwable == null){
                listUserString =usersList.map { it.name }
                mView?.setAdapter(listUserString)
            }else{

            }
            mView?.hideLoading()
    })
        return listUserString
}

}