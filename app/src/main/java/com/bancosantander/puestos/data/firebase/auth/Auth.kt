package com.bancosantander.puestos.data.firebase.auth

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class Auth private constructor() {

	private val auth: FirebaseAuth = FirebaseAuth.getInstance()

	fun isLogedIn(): Boolean = auth.currentUser != null
	fun isNotLogedIn(): Boolean = !isLogedIn()
	fun getEmail(): String? = auth.currentUser?.email

	fun addAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
		auth.addAuthStateListener(listener)
	}
	fun delAuthStateListener(listener: FirebaseAuth.AuthStateListener) {
		auth.removeAuthStateListener(listener)
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