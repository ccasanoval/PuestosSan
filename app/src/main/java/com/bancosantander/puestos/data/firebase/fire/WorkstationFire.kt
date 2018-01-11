package com.bancosantander.puestos.data.firebase.fire

import android.support.annotation.WorkerThread
import android.support.v7.app.AppCompatActivity
import com.bancosantander.puestos.data.models.User
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.util.Log
import java.util.*
import kotlin.collections.HashMap
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*
import kotlinx.coroutines.experimental.async


object WorkstationFire {
	private val TAG: String = WorkstationFire::class.java.simpleName
	private val ROOT_COLLECTION = "workstations"
	private val ROOT_COLLECTION_DATES_FREE = "datesFree"
	private val ROOT_COLLECTION_DATES_OCCUPIED = "datesOccupied"
	private val ROOT_SUBCOLLECTION = "puestos"
	private val POSITION_FIELD = "position"

	/*fun getAll(fire: Fire, callback: (ArrayList<Workstation>, Throwable?) -> Unit) {
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
	}*/
	/*fun getAllRT(fire: Fire, callback: (ArrayList<Workstation>, Throwable?) -> Unit) {
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
		}*/
	fun getFreeWithDateRT(fire: Fire, date: String, callback: (ArrayList<Workstation>, Throwable?) -> Unit) {
		val document = fire.getCol(ROOT_COLLECTION_DATES_FREE).document(date)
		document.collection(ROOT_SUBCOLLECTION)
				.addSnapshotListener({ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    val res = arrayListOf<Workstation>()
                    if(error == null && data != null) {
                        if (data.isEmpty){
                            callback(res,null)
                        }
                        data.forEach { doc ->
                            val owner = doc.data["owner"] as String
                            getWorkstation(fire,owner, { workstation, throwable ->
                                if(throwable == null) {
                                    res.add(workstation)
                                    callback(res, null)
                                }
                                else {
                                    callback(res, throwable)
                                    Log.e(TAG, "getWorkstation:e:----------------------------------------------------", throwable)
                                }
                            })
                        }
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
	fun getWorkstation(fire:Fire,owner:String,callback: (Workstation, Throwable?) -> Unit) {
		fire.getCol(ROOT_COLLECTION)
                .whereEqualTo("idOwner", owner)
				.get()
				.addOnCompleteListener({ task ->
					lateinit var res: Workstation
					if(task.isSuccessful && task.result.documents.size > 0) {
							val puesto = createPuestoHelper(fire, task.result.documents[0])
							if (puesto?.idOwner == owner ) callback(puesto, null)
					}
					else {
						callback(res, task.exception)
						Log.e(TAG, "getAll:e:------------------------------------------------------", task.exception)
					}
                })
	}
	/*fun getWorkstationRT(context: AppCompatActivity, fire:Fire, user: String,type: String, callback: (Workstation?, Throwable?) -> Unit) {
        fire.getCol(ROOT_COLLECTION)
                .whereEqualTo(type,user)
                .addSnapshotListener(context,{ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
                    lateinit var res: Workstation
                    if(error == null && data != null) {
                        if(data.isEmpty || data.documents.isEmpty()){
                            callback(null,null)
                        }else{
                            data.forEach { doc ->
                                val puesto = createPuestoHelper(fire, doc)
                                callback(puesto, null)
                            }
                        }

                    }
                    else {
                        callback(res, error)
                        Log.e(TAG, "getAllRT:e:----------------------------------------------------", error)
                    }
                })
    }*/
    fun getWorkstationRTV2(context: AppCompatActivity, fire:Fire, user: String,type: String,date:String, callback: (Workstation?, Throwable?) -> Unit) {
        val freeWorkstationIds = fire.getCol(ROOT_COLLECTION_DATES_FREE).document(date).collection(ROOT_SUBCOLLECTION)
        val occupiedWorkstationIds = fire.getCol(ROOT_COLLECTION_DATES_OCCUPIED).document(date).collection(ROOT_SUBCOLLECTION)
        if (type.equals(User.IdType.idOwner.name)) {
            fire.getCol(ROOT_COLLECTION)
                    .whereEqualTo("idOwner",user)
                    .addSnapshotListener(context,{ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
                        var res: Workstation?
                        if (error != null){
                            callback(null, error)
                            Log.e(TAG, "getAllRT:e:----------------------------------------------------", error)
                        } else if(data != null && (data.isEmpty || data.documents.isEmpty())){
                            callback(null, null)
                        } else{
                            data?.forEach { doc ->
                                res = createPuestoHelper(fire, doc)
                                isFreeOrOccupied(freeWorkstationIds,occupiedWorkstationIds,res,user,callback )
                            }
                        }

                    })
        }else {
            occupiedWorkstationIds.whereEqualTo("idUser",user).addSnapshotListener(context,{ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
                if (error != null){
                    callback(null, error)
                    Log.e(TAG, "getAllRT:e:----------------------------------------------------", error)
                } else if(data != null && (data.isEmpty || data.documents.isEmpty())){
                    callback(null, null)
                } else{
                    data?.forEach { doc ->
                        val owner = doc.get("owner") as String
                        fire.getCol(ROOT_COLLECTION)
                                .whereEqualTo("idOwner",owner)
                                .addSnapshotListener(context,{ data: QuerySnapshot?, error: FirebaseFirestoreException? ->
                                    var res: Workstation?
                                    if (error != null){
                                        callback(null, error)
                                        Log.e(TAG, "getAllRT:e:----------------------------------------------------", error)
                                    } else if(data != null && (data.isEmpty || data.documents.isEmpty())){
                                        callback(null, null)
                                    } else{
                                        data?.forEach { doc ->
                                            res = createPuestoHelper(fire, doc)
                                            isFreeOrOccupied(freeWorkstationIds,occupiedWorkstationIds,res,owner,callback )
                                            //callback(res, null)
                                        }
                                    }

                                })
                    }
                }

            })
        }


    }

    private fun isFreeOrOccupied(freeRef:CollectionReference,occupiedRef:CollectionReference,workstation: Workstation?, user:String, callback: (Workstation?, Throwable?) -> Unit){

        freeRef.whereEqualTo("owner",user).get().addOnCompleteListener { task ->
            if(task.isSuccessful and !task.result.documents.isEmpty()) {
                callback(changeWorkstationStatus(true,false,workstation,user),null)
            }else {
                occupiedRef.whereEqualTo("owner",user).get().addOnCompleteListener { task ->
                    if(task.isSuccessful and !task.result.documents.isEmpty()) {
                        val idUser = task.result.documents[0].get("idUser") as String
                        callback(changeWorkstationStatus(false,true,workstation,idUser),null)
                    }else {
                        callback(changeWorkstationStatus(false,false,workstation,user),null)
                    }
                }
            }
        }
    }

   private fun changeWorkstationStatus(free: Boolean, occupied: Boolean, puesto: Workstation?, user: String) : Workstation {
       val copy = puesto?.copy()
          if (!free && !occupied) {
             copy?.apply {
                 idUser = user
                 status = Workstation.Status.Occupied
             }
         } else if (free) {
             copy?.apply {
                 idUser = ""
                 status = Workstation.Status.Free
             }
         } else {

             copy?.apply {
                 idUser = user
                 status = Workstation.Status.Occupied
             }
         }
       return copy!!

    }

    fun releaseMyWorkstation(fire:Fire,owner: String ,callback: (Workstation, Throwable?) -> Unit){
        fire.getCol(ROOT_COLLECTION)
                .whereEqualTo("idOwner", owner)
                .get()
                .addOnCompleteListener({task ->
                    lateinit var res: Workstation
                    if(task.isSuccessful) {
                        val puesto = createPuestoHelper(fire, task.result.documents[0])
                        puesto?.let {
                            puesto.status = Workstation.Status.Free
							puesto.idUser = "";
                            callback(changeWorkstationStatus(true,false,puesto,""),null)
                        }
                    }
                    else {
                        callback(res, task.exception)
                        Log.e(TAG, "getAll:e:------------------------------------------------------", task.exception)
                    }
                })
    }
    fun fillWorkstation (fire:Fire,owner: String,user : String ,callback: (Workstation, Throwable?) -> Unit) {

        fire.getCol(ROOT_COLLECTION)
                .whereEqualTo("idOwner", owner)
                .get()
                .addOnCompleteListener({task ->
                    lateinit var res: Workstation
                    if(task.isSuccessful) {
                        val puesto = createPuestoHelper(fire, task.result.documents[0])
                        puesto?.let {
                            callback(changeWorkstationStatus(false,true,puesto,user),null)
                        }
                    }
                    else {
                        callback(res, task.exception)
                        Log.e(TAG, "fillWorkstation:e:------------------------------------------------------", task.exception)
                    }
                })
    }

     suspend fun releaseMyWorkstationV2(fire:Fire, owner: String, date: String, callback: (Workstation, Throwable?) -> Unit){

        val workstationsFree = fire.getCol(ROOT_COLLECTION_DATES_OCCUPIED).document(date).collection(ROOT_SUBCOLLECTION)
        val queryFree = workstationsFree.whereEqualTo("owner", owner)

        val delete = async {
            deleteQueryBatch(queryFree)
        }
        val addRelease = async {
            addReleaseWorkstation(fire, date, owner)
        }

        delete.await()
        addRelease.await()

        releaseMyWorkstation(fire,owner,callback)


    }

     suspend fun fillWorkstationV2 (fire:Fire, owner: String, user : String, date:String, callback: (Workstation, Throwable?) -> Unit) {
        val workstationsFree = fire.getCol(ROOT_COLLECTION_DATES_FREE).document(date).collection(ROOT_SUBCOLLECTION)
        val queryFree = workstationsFree.whereEqualTo("owner", owner)
        val delete = async {
            deleteQueryBatch(queryFree)
        }
        val addFill = async {
            addFillWorkstation(fire, date, owner, user)
        }
        delete.await()
        addFill.await()
        fillWorkstation(fire,owner,user,callback)

    }

    suspend private fun addReleaseWorkstation(fire: Fire, date: String, owner: String) {

        val workstationsFree = fire.getCol(ROOT_COLLECTION_DATES_FREE).document(date).collection(ROOT_SUBCOLLECTION)
        workstationsFree.whereEqualTo("owner",owner).get().addOnCompleteListener { task ->
            if(task.isSuccessful and task.result.isEmpty){
                val hashMap = HashMap<String, Any>()
                hashMap.put("owner", owner)
                workstationsFree.add(hashMap)
            }
        }
    }

    suspend private fun addFillWorkstation(fire: Fire, date: String, owner: String, user: String) {
        val workstationsOccupied = fire.getCol(ROOT_COLLECTION_DATES_OCCUPIED).document(date).collection(ROOT_SUBCOLLECTION)
        workstationsOccupied.whereEqualTo("owner",owner).get().addOnCompleteListener { task ->
            if(task.isSuccessful and task.result.isEmpty){
                val hashMap = HashMap<String, Any>()
                if (owner != user) {
                    hashMap.put("owner", owner)
                    hashMap.put("idUser", user)
                    workstationsOccupied.add(hashMap)
                }
            }
        }
    }

    /**
     * Delete all results from a query in a single WriteBatch. Must be run on a worker thread
     * to avoid blocking/crashing the main thread.
     */
    @WorkerThread
    @Throws(Exception::class)
    suspend private fun deleteQueryBatch(query: Query): List<DocumentSnapshot> {
        val querySnapshot = Tasks.await(query.get())
        val batch = query.getFirestore().batch()
        for (snapshot in querySnapshot) {
            batch.delete(snapshot.getReference())
        }
        Tasks.await<Void>(batch.commit())
        return querySnapshot.getDocuments()
    }
}