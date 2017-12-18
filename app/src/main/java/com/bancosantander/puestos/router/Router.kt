package com.bancosantander.puestos.router

import android.content.Context
import com.bancosantander.puestos.ui.activities.login.LoginActivity
import com.bancosantander.puestos.ui.activities.map.MapaActivity
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import org.jetbrains.anko.startActivity

class Router(val context:Context){

    fun goToManageOwnWorkstation(){
        context.startActivity<OwnWorkstationActivity>()
    }

    fun goToFillWorkstation() {
        context.startActivity<MapaActivity>()
    }
    fun goToLogin(){
        context.startActivity<LoginActivity>()
    }
}