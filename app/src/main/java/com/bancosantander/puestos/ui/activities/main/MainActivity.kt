package com.bancosantander.puestos.ui.activities.main

import android.os.Bundle
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.presenters.MainPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity

class MainActivity : BaseMvpActivity<MainViewContract.View,
        MainPresenter>(),
        MainViewContract.View {


    override  var mPresenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.init()

    }


    companion object {
        val TAG = MainActivity::class.java.simpleName
    }


}