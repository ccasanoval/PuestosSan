package com.mibaldi.viewmodelexamplemvp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.app
import com.google.firebase.auth.FirebaseAuth

/**
 * Created by mbalduciel on 16/12/17.
 */
abstract class BaseMvpFragment<in V : BaseMvpFragmentView>
    : Fragment(), BaseMvpFragmentView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


}

