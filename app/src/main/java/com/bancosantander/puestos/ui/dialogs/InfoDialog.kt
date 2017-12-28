package com.bancosantander.puestos.ui.dialogs

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

class InfoDialog {
	companion object {
		fun newInstance(context: Context, msj: String) {
			val builder = AlertDialog.Builder(context)
			builder.setMessage(msj)
					.setNeutralButton("OKEY", DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
					.show()
		}
	}
}