package com.bancosantander.puestos.data.firebase.fire

import com.bancosantander.puestos.util.Log
import com.google.firebase.firestore.*

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

	fun translate(res: QuerySnapshot, clase: Class<*>): Any? {

		try {
			return res.documents
		}
		catch(e: Exception) {
			Log.e("Fire", "translate:e:----------------------------------------------------",e)
		}
		return null
	}
}
