package com.bancosantander.puestos.data.firebase.fire

import com.bancosantander.puestos.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class Fire {

	private val db = FirebaseFirestore.getInstance()

	fun getCol(collection: String): CollectionReference
			= db.collection(collection)
	fun getDoc(collection: String, document: String): DocumentReference
			= db.collection(collection).document(document)

	fun translate(res: DocumentSnapshot, clase: Class<*>): Any? {

		try {
			return res.toObject(clase)
		}
		catch(e: Exception) {
			Log.e("Fire", "translate:e:----------------------------------------------------"+res.data,e)
		}
		return null
	}
}
