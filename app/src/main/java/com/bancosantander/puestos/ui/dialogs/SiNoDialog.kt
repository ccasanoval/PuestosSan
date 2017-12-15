package com.bancosantander.puestos.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

class SiNoDialog {
	companion object {
		fun showSiNo(context: Context, msj: String, callback: (b: Boolean) -> Unit) {
			val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
				callback(which == DialogInterface.BUTTON_POSITIVE)
			}
			val builder = AlertDialog.Builder(context)
			builder.setMessage(msj).setPositiveButton("Sí", dialogClickListener)
				.setNegativeButton("No", dialogClickListener).show()
		}
	}
}