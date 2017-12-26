package com.bancosantander.puestos.ui.activities.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.ui.presenters.MainPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.R.id.edit



class MainActivity : BaseMvpActivity<MainViewContract.View,
        MainPresenter>(),
        MainViewContract.View {


    override  var mPresenter: MainPresenter = MainPresenter()
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.init()
        prefs = getSharedPreferences(PREFERENCES,Context.MODE_PRIVATE)

        checkIfIsFirstLogin()

        btnManage.setOnClickListener { mPresenter.goToManageOwnWorkstation() }
        btnFill.setOnClickListener { mPresenter.goToFillWorkstation() }
        btnLogout.setOnClickListener { mPresenter.logout() }
        btnConfiguration.setOnClickListener{mPresenter.gotToConfiguration()}
    }

    private fun checkIfIsFirstLogin() {
        if(!prefs.getBoolean(mPresenter.auth().getEmail(),false)){
            mPresenter.gotToConfiguration()
            val editor = prefs.edit()
            editor.putBoolean(mPresenter.auth().getEmail(),true)
        }
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        val PREFERENCES = "puestos_login_preferences"
    }


}