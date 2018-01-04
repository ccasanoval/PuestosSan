package com.bancosantander.puestos.application

import android.app.Application
import com.bancosantander.puestos.data.firebase.auth.Auth
import com.bancosantander.puestos.data.firebase.fire.Fire
import com.bancosantander.puestos.router.Router
import org.greenrobot.eventbus.EventBus




// ZOOM ImageView:
//https://blog.fossasia.org/implementing-a-zoomable-imageview-by-extending-the-default-viewpager-in-phimpme-android/
//https://github.com/Piasy/BigImageViewer

// Arch Comp
//https://developer.android.com/topic/libraries/architecture/adding-components.html

// PATH FINDING
//https://stackoverflow.com/questions/25120786/a-star-algorithm-with-image-map-android
//https://github.com/citiususc/hipster  TOO SLOW AND MEMORY CONSUMING / java.lang.OutOfMemoryError
//https://www.bedroomlan.org/projects/libastar IN C
//https://github.com/leethomason/MicroPather IN C
//https://github.com/justinhj/astar-algorithm-cpp IN C

// INDOOR LOCATION
//https://play.google.com/store/apps/details?id=com.microsoft.msra.followus.app
//https://arxiv.org/ftp/arxiv/papers/1405/1405.5669.pdf & what u did

// ATOMIC CALLS FIRESTORE
//https://firebase.google.com/docs/firestore/manage-data/transactions

//TODO: Login: Admin: Permite añadir puestos de trabajo, modificarlos, borrarlos...
//						Permite añadir y editar y borrar usuarios
//						Permite asignar puestos de trabajo
//				User: Permite bucar y ver puestos de trabajo...
//						Permite reservar puestos de trabajo
//TODO: showWorkstations, showFreeWorkstations(day), ...

class App : Application() {
	lateinit var auth: Auth
	lateinit var fire: Fire
	var mRouter: Router = Router(this)
	override fun onCreate() {
		super.onCreate()

		/// FIRE AUTH
		auth = Auth.getInstance(this)
		fire = Fire()

		/// EVENT BUS
		EventBus.builder().addIndex(com.bancosantander.puestos.RutaEventIndex()).installDefaultEventBus()
	}
	fun getRouter():Router{
		return mRouter
	}
}