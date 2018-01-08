package com.bancosantander.puestos.base

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.ui.activities.login.LoginActivity
import com.bancosantander.puestos.ui.dialogs.InfoDialog
import com.bancosantander.puestos.util.app
import com.google.firebase.auth.FirebaseAuth

abstract class BaseActivity: AppCompatActivity() {

	private var authListener = FirebaseAuth.AuthStateListener {
		if ((application as App).auth.isNotLogedIn())
			app.getRouter().goToLogin()
		else
			Log.e("BaseActivity", "onCreate:-2---------------------USR:" + (application as App).auth.getEmail())
	}

	override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
		super.onCreate(savedInstanceState, persistentState)
		onCreate(savedInstanceState)
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		val auth: Auth = (application as App).auth
		auth.addAuthStateListener(authListener)
		if(auth.isNotLogedIn()) {
			app.getRouter().goToLogin()
		}
		else {
			Log.e("BaseActivity", "onCreate:----------------------USR:"+auth.getEmail())
		}
	}
	override fun onDestroy() {
		super.onDestroy()
		Log.e("BaseActivity", "onDestroy:--------------------------------------")
		(application as App).auth.delAuthStateListener(authListener)
	}

}
