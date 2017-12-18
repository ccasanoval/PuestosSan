package com.mibaldi.viewmodelexamplemvp.base

import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.data.firebase.fire.Fire
import com.bancosantander.puestos.router.Router

/**
 * Created by mbalduciel on 16/12/17.
 */
interface BaseMvpPresenter<in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()
    fun router(): Router
    fun auth(): Auth
    fun fire(): Fire
}