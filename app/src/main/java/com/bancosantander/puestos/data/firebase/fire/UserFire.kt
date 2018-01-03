package com.bancosantander.puestos.data.firebase.fire

import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.util.Log

/**
 * Created by ccasanova on 01/12/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
object UserFire {
	private val TAG: String = User::class.java.simpleName
	private val ROOT_COLLECTION = "users"

	//______________________________________________________________________________________________
	fun get(fire: Fire, email: String?, callback: (User, Throwable?) -> Unit) {
		email?.let{
			fire.getDoc(ROOT_COLLECTION, it)
					.get()
					.addOnCompleteListener({ task ->
						if(task.isSuccessful) {
							val user = fire.translate(task.result, User::class.java) as User
							callback(user, null)
						}
						else {
							Log.e(TAG, "loadComicList:Firebase:e:------------------------ ", task.exception)
							callback(User(), task.exception)
						}
					})
		}

	}
}