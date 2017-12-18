package com.mibaldi.viewmodelexamplemvp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by mbalduciel on 16/12/17.
 */
abstract class BaseMvpActivity<in V : BaseMvpView, T : BaseMvpPresenter<V>>
    : AppCompatActivity(), BaseMvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
    }

    override fun getActivity(): AppCompatActivity {
        return this
    }
    protected abstract var mPresenter: T


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }





}

