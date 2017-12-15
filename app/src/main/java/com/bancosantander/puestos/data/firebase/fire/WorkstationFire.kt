package com.bancosantander.puestos.data.firebase.fire

import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.QuerySnapshot

object WorkstationFire {
	private val TAG: String = WorkstationFire::class.java.simpleName
	private val ROOT_COLLECTION = "workstations"
	private val POSITION_FIELD = "position"

	fun getAll(fire: Fire, callback: (ArrayList<Workstation>, Throwable?) -> Unit) {
		fire.getCol(ROOT_COLLECTION)
			.get()
			.addOnCompleteListener({ task ->
				val res = arrayListOf<Workstation>()
				if(task.isSuccessful) {
					for(doc in task.result) {
						val puesto = createPuestoHelper(fire, doc)
						if(puesto != null) res.add(puesto)
					}
					callback(res, null)
				}
				else {
					callback(res, task.exception)
					Log.e(TAG, "getAll:e:------------------------------------------------------", task.exception)
				}
			})
	}
	fun getAllRT(fire: Fire, callback: (ArrayList<Workstation>, Throwable?) -> Unit) {
		fire.getCol(ROOT_COLLECTION)
			.addSnapshotListener({ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
				val res = arrayListOf<Workstation>()
				if(error == null && data != null) {
					data.forEach { doc ->
						val puesto = createPuestoHelper(fire, doc)
						if(puesto != null) res.add(puesto)
					}
					callback(res, null)
				}
				else {
					callback(res, error)
					Log.e(TAG, "getAllRT:e:----------------------------------------------------", error)
				}
			})
		}

	fun createPuestoHelper(fire: Fire, doc: DocumentSnapshot): Workstation? {
		val puesto = fire.translate(doc, Workstation::class.java) as Workstation?
		if(puesto != null) {
			val pos: GeoPoint = doc.get(POSITION_FIELD) as GeoPoint
			return puesto.copy(pos.longitude.toFloat(), pos.latitude.toFloat())
		}
		return null
	}
}