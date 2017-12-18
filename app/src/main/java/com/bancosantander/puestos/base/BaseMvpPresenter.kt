package com.mibaldi.viewmodelexamplemvp.base

/**
 * Created by mbalduciel on 16/12/17.
 */
interface BaseMvpPresenter<in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()
}