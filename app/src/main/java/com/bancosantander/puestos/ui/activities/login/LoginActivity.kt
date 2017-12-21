package com.bancosantander.puestos.ui.activities.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.R
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.ui.activities.main.MainActivity
import kotlinx.android.synthetic.main.act_login.*


class LoginActivity : AppCompatActivity(){

	private lateinit var auth: Auth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.act_login)
		btnLogin.setOnClickListener { signIn(txtLoginEmail.text.toString()+"@puestos.com", txtLoginClave.text.toString()) }
		btnLogout.setOnClickListener { signOut() }
		auth = (application as App).auth
	}

	override fun onStart() {
		super.onStart()
		updateUI(auth.getEmail())
	}

	private fun signIn(email: String, password: String) {
		Log.e(TAG, "signIn:----------------------------------------" + email)
		if(!validateForm()) {
			return
		}

		showProgressDialog()

		auth.login(email, password, { auth: Auth, exception: Exception? ->
			if(auth.isLogedIn() && exception == null) {
				Log.e(TAG, "signInWithEmail:success----------------------")
					updateUI(auth.getEmail())
					val intent = Intent(this, MainActivity::class.java)
					startActivity(intent)
					finish()
			}
			else {
				Log.e(TAG, "signInWithEmail:failure-------------------------", exception)
					Toast.makeText(this, R.string.error_login, Toast.LENGTH_SHORT).show()
					updateUI(null)
				txtLoginStatus.setText(R.string.error_login)
			}
		})
	}

	private fun signOut() {
		auth.logout()
		updateUI(null)
	}

	private fun validateForm(): Boolean {
		var valid = true
		val email = txtLoginEmail.text.toString()
		if(TextUtils.isEmpty(email)) {
			txtLoginEmail.error = getString(R.string.campo_obligatorio)
			valid = false
		}
		else {
			txtLoginEmail.error = null
		}

		val password = txtLoginClave.text.toString()
		if(TextUtils.isEmpty(password)) {
			txtLoginClave.error = getString(R.string.campo_obligatorio)
			valid = false
		}
		else {
			txtLoginClave.error = null
		}

		return valid
	}

	private fun updateUI(userEmail: String?, isVerifiedEmail: Boolean? = false) {
		hideProgressDialog()
		if(userEmail != null) {
			txtLoginStatus.text = getString(R.string.ok_login, userEmail, isVerifiedEmail)

			var view: View = findViewById(R.id.layLoginBotones)
			view.visibility=(View.GONE)
			view = findViewById(R.id.layLoginFields)
			view.visibility=(View.GONE)
			view = findViewById(R.id.layLogoutBotones)
			view.visibility=(View.VISIBLE)

			finish()
		}
		else {
			txtLoginStatus.setText(R.string.sesion_cerrada)

			var view: View = findViewById(R.id.layLoginBotones)
			view.visibility=(View.VISIBLE)
			view = findViewById(R.id.layLoginFields)
			view.visibility=(View.VISIBLE)
			view = findViewById(R.id.layLogoutBotones)
			view.visibility=(View.GONE)
		}
	}


	//TODO: cambiar progressDialog
	@VisibleForTesting
	var mProgressDialog: ProgressDialog? = null

	fun showProgressDialog() {
		if(mProgressDialog == null) {
			mProgressDialog = ProgressDialog(this)
			mProgressDialog!!.setMessage(getString(R.string.cargando))
			mProgressDialog!!.isIndeterminate = true
		}

		mProgressDialog!!.show()
	}

	fun hideProgressDialog() {
		if(mProgressDialog != null && mProgressDialog!!.isShowing) {
			mProgressDialog!!.dismiss()
		}
	}

	public override fun onStop() {
		super.onStop()
		hideProgressDialog()
	}

	companion object {
		private val TAG = LoginActivity::class.java.simpleName
	}
}
