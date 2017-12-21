package com.bancosantander.puestos.data.firebase.fire

import android.support.v7.app.AppCompatActivity
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

	fun getAllRT(fire: Fire, callback: (ArrayList<Workstation>, Throwable?) -> Unit) {
		fire.getCol(ROOT_COLLECTION)
                .whereEqualTo("status", Workstation.Status.Free.name)
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
			return puesto.setPosition(pos.longitude.toFloat(), pos.latitude.toFloat())
		}
		return null
	}

	fun getWorkstationRT(context: AppCompatActivity, fire:Fire, owner:String, callback: (Workstation?, Throwable?) -> Unit) {
		fire.getCol(ROOT_COLLECTION)
                .whereEqualTo("idOwner", owner)
				.addSnapshotListener(context,{ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
					lateinit var res: Workstation
					if(error == null && data != null) {
						if(data.isEmpty || data.documents.isEmpty()){
							callback(null,null)
							//TODO: no se debería hacer esto, no es lo mismo nulo que vacio...
							//TODO: el cliente deberia comprobar la nulidad y la cantidad de elementos por separado
						}else{
							data.forEach { doc ->
								val puesto = createPuestoHelper(fire, doc)
								if (puesto?.idOwner == owner ) callback(puesto, null)
							}
						}
					}
					else {
						callback(res, error)
						Log.e(TAG, "getAllRT:e:----------------------------------------------------", error)
					}
				})

	}
    fun releaseMyWorkstation(fire:Fire,owner: String ,callback: (Workstation, Throwable?) -> Unit){
        fire.getCol(ROOT_COLLECTION)
                .whereEqualTo("idOwner", owner)
                .get()
                .addOnCompleteListener({task ->
                    lateinit var res: Workstation
                    if(task.isSuccessful) {
                        task.result.documents[0].reference.update("status",Workstation.Status.Free.name)
                        val puesto = createPuestoHelper(fire, task.result.documents[0])
                        puesto?.let {
                            puesto.status = Workstation.Status.Free
                            callback(puesto,null)
                        }
                    }
                    else {
                        callback(res, task.exception)
                        Log.e(TAG, "getAll:e:------------------------------------------------------", task.exception)
                    }
                })

    }
	fun fillWorkstation (fire:Fire,owner: String ,callback: (Workstation, Throwable?) -> Unit) {
		fire.getCol(ROOT_COLLECTION)
				.whereEqualTo("idOwner", owner)
				.get()
				.addOnCompleteListener({task ->
					lateinit var res: Workstation
					if(task.isSuccessful) {
						task.result.documents[0].reference.update("status",Workstation.Status.Occupied.name)
						val puesto = createPuestoHelper(fire, task.result.documents[0])
						puesto?.let {
							puesto.status = Workstation.Status.Occupied
							callback(puesto,null)
						}
					}
					else {
						callback(res, task.exception)
						Log.e(TAG, "fillWorkstation:e:------------------------------------------------------", task.exception)
					}
				})
	}
}