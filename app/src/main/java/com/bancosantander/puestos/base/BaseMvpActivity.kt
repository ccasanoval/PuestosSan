package com.mibaldi.viewmodelexamplemvp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.app
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by mbalduciel on 16/12/17.
 */
abstract class BaseMvpActivity<in V : BaseMvpView, T : BaseMvpPresenter<V>>
    : AppCompatActivity(), BaseMvpView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.attachView(this as V)
        val auth: Auth = (application as App).auth
        auth.addAuthStateListener(authListener)
        if(auth.isNotLogedIn()) {
            app.getRouter().goToLogin()
        }
        else {
            Log.e("BaseActivity", "onCreate:----------------------USR:"+auth.getEmail())
        }
    }

    override fun getMyActivity(): AppCompatActivity {
        return this
    }
    abstract var mPresenter: T


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        Log.e("BaseActivity", "onDestroy:--------------------------------------")
        (application as App).auth.delAuthStateListener(authListener)
    }


    private var authListener = FirebaseAuth.AuthStateListener {
        if((application as App).auth.isNotLogedIn())
            app.getRouter().goToLogin()
        else
            Log.e("BaseActivity", "onCreate:-2---------------------USR:"+(application as App).auth.getEmail())
    }





}

