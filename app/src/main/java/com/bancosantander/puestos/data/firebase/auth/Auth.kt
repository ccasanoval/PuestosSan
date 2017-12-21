package com.bancosantander.puestos.data.firebase.auth

import android.app.Application
import android.util.Log
import com.bancosantander.puestos.application.App
import com.bancosantander.puestos.data.firebase.fire.Fire
import com.bancosantander.puestos.data.firebase.fire.UserFire
import com.bancosantander.puestos.data.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult


class Auth private constructor() {

	private val auth: FirebaseAuth = FirebaseAuth.getInstance()
	private val fire = Fire()

	fun isLogedIn(): Boolean = auth.currentUser != null
	fun isNotLogedIn(): Boolean = !isLogedIn()
	fun getEmail(): String? = auth.currentUser?.email
	fun getUserFire(func: (User,Throwable?) -> Unit) { UserFire.get(fire,getEmail()!!,func) }

	fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
		auth.addAuthStateListener(listener)
	}
	fun delAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
		auth.removeAuthStateListener(listener)
	}
	fun changePassword(oldPass: String,pass: String,callback: (Boolean,Throwable?)->Unit){

		val credential = EmailAuthProvider.getCredential(getEmail()!!, oldPass)
		val currentUser = auth.currentUser!!
		currentUser.reauthenticateAndRetrieveData(credential)!!
				.addOnCompleteListener { task: Task<AuthResult> ->
					if (task.isSuccessful){
						currentUser.updatePassword(pass).addOnCompleteListener { task ->
							if (task.isSuccessful){
								callback(true,null)
								Log.d("PASS", "Password updated");
							} else {
								callback(false,task.exception)
								Log.d("PASS", "Error password not updated")
							}
						}
					}else {
						callback(false,task.exception)
						Log.d("PASS", "Error password not updated")
					}
				}
	}

	fun login(email: String, clave: String, callback: (Auth, Exception?)->Unit) {
		//TODO: listener: com.google.android.gms.tasks.OnCompleteListener<Auth>) {
		auth.signInWithEmailAndPassword(email, clave)
			.addOnCompleteListener { auth2 ->
				callback(this, auth2.exception)
			}
	}

	fun logout() {
		auth.signOut()
	}


	companion object {
		fun getInstance(app: Application): Auth {
			FirebaseApp.initializeApp(app)
			return Auth()
		}
	}
}