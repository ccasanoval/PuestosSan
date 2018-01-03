package com.bancosantander.puestos.ui.dialogs

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.bancosantander.puestos.R
import kotlinx.android.synthetic.main.dialog_change_password.view.*

class ChangePassDialog {
	companion object {
		fun newInstance(activity: Activity,title:String="Cambiar Contraseña", callback:(oldPass:String,newPass:String)->Unit){
			val dialogBuilder = AlertDialog.Builder(activity)
			val inflater = activity.layoutInflater
			val dialogView = inflater.inflate(R.layout.dialog_change_password, null)

			val dialog = dialogBuilder
					.setView(dialogView)
					.setTitle(title)
					.setCancelable(false)
					.setPositiveButton("Cambiar", { dialog, whichButton ->
						callback(dialogView.etOldPassword.text.toString(),dialogView.etPassword.text.toString())
						dialog.dismiss()
					})
					.setNegativeButton("Cancelar", { dialog, whichButton ->
						callback("","")
						dialog.dismiss()
					})
					.create()
			dialog.show()
		}
	}
}