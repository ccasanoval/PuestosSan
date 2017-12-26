package com.bancosantander.puestos.ui.activities.main

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.presenters.MainPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainViewContract.View,
        MainPresenter>(),
        MainViewContract.View {


    override  var mPresenter: MainPresenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.init()
        btnManage.setOnClickListener { mPresenter.goToManageOwnWorkstation() }
        btnFill.setOnClickListener { mPresenter.goToFillWorkstation() }
        btnLogout.setOnClickListener { mPresenter.logout() }
        btnConfiguration.setOnClickListener{mPresenter.gotToConfiguration()}
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }


}