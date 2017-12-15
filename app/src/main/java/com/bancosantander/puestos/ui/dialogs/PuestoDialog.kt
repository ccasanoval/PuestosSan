package com.bancosantander.puestos.ui.dialogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.util.WorkstationParcelable
import kotlinx.android.synthetic.main.dlg_puesto.*



class PuestoDialog : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.dlg_puesto)

		//TODO:
		val params = window.attributes
		params.x = -10
		params.y = -10
		params.height = 1600
		params.width = 1000

		this.window.attributes = params

		val puesto: WorkstationParcelable = intent.extras.getParcelable(Workstation::class.java.name)
		txtOwner.setText(puesto.puesto.idOwner)
		txtUser.setText(puesto.puesto.idUser)
		txtName.setText(puesto.puesto.name)
		txtStatus.setText(when(puesto.puesto.status) {
			Workstation.Status.Free -> getString(R.string.free)
			Workstation.Status.Occupied -> getString(R.string.occupied)
			Workstation.Status.Unavailable -> getString(R.string.unavailable)
		})

	}
}
