package com.bancosantander.puestos.router

import android.content.Context
import android.content.Intent
import com.bancosantander.puestos.ui.activities.configuration.ConfigurationActivity
import com.bancosantander.puestos.ui.activities.login.LoginActivity
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationMapActivity
import com.bancosantander.puestos.ui.activities.searchUser.SearchUserActivity
import com.bancosantander.puestos.ui.activities.tutorial.TutorialActivity
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity

class Router(val context:Context){

    fun goToManageOwnWorkstation(){
        val intent = Intent(context, OwnWorkstationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun goToFillWorkstation() {
        val intent = Intent(context, WorkstationsActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)    }
    fun goToLogin(){
        val intent = Intent(context, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun goToConfiguration() {
        val intent = Intent(context, ConfigurationActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun goToTutorial() {
        val intent = Intent(context, TutorialActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun gotoSearchUser() {
        val intent = Intent(context, SearchUserActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

	fun goToMap() {
		val intent = Intent(context, OwnWorkstationMapActivity::class.java)
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
		context.startActivity(intent)
	}

}