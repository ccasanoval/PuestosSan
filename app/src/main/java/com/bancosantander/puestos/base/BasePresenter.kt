package com.mibaldi.viewmodelexamplemvp.base

import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.data.firebase.fire.Fire
import com.bancosantander.puestos.router.Router
import com.bancosantander.puestos.util.app

/**
 * Created by mbalduciel on 16/12/17.
 */
open class BasePresenter<V : BaseMvpView> : BaseMvpPresenter<V> {

    protected var mView: V? = null

    override fun attachView(view: V) {
        mView = view
    }


    override fun detachView() {
        mView = null
    }
    override fun router():Router{
        return mView?.getMyActivity()?.app?.getRouter()!!
    }

    override fun auth(): Auth {
        return mView?.getMyActivity()?.app?.auth!!
    }
    override fun fire(): Fire {
        return mView?.getMyActivity()?.app?.fire!!
    }
}