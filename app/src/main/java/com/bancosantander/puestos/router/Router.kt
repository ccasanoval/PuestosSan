package com.bancosantander.puestos.router

import android.content.Context
import android.content.Intent
import com.bancosantander.puestos.ui.activities.login.LoginActivity
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity

class Router(val context:Context){

    fun goToManageOwnWorkstation(){
        val intent = Intent(context, OwnWorkstationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)    }

    fun goToFillWorkstation() {
        val intent = Intent(context, WorkstationsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)    }
    fun goToLogin(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}