package com.bancosantander.puestos.router

import android.content.Context
import android.content.Intent
import com.bancosantander.puestos.ui.activities.login.LoginActivity
import com.bancosantander.puestos.ui.activities.map.MapaActivity
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import org.jetbrains.anko.startActivity

class Router(val context:Context){

    fun goToManageOwnWorkstation(){
        context.startActivity<OwnWorkstationActivity>()
    }

    fun goToFillWorkstation() {
        val intent = Intent(context, WorkstationsActivity::class.java)
        context.startActivity(intent)
    }
    fun goToLogin(){
        context.startActivity<LoginActivity>()
    }
}