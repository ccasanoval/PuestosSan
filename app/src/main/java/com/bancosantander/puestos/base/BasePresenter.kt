package com.mibaldi.viewmodelexamplemvp.base

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
}