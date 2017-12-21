package com.mibaldi.viewmodelexamplemvp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.app
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by mbalduciel on 16/12/17.
 */
abstract class BaseMvpFragment<in V : BaseMvpView, T : BaseMvpPresenter<V>>
    : Fragment(), BaseMvpFragmentView {


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this as V)
    }
    abstract var mPresenter: T
    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }


}

