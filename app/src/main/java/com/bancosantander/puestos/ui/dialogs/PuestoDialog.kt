package com.bancosantander.puestos.ui.dialogs

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.views.ViewField.enlaza
import com.bancosantander.puestos.util.WorkstationParcelable


/**
 * Created by ccasanova on 15/12/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
class PuestoDialog : AppCompatActivity() {

	//private val txtId: TextView by enlaza(R.id.txtId)
	private val txtOwner: TextView by enlaza(R.id.txtOwner)
	private val txtUser: TextView by enlaza(R.id.txtUser)
	private val txtName: TextView by enlaza(R.id.txtName)
	private val txtStatus: TextView by enlaza(R.id.txtStatus)

	//______________________________________________________________________________________________
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
		txtOwner.text = puesto.puesto.idOwner
		txtUser.text = puesto.puesto.idUser
		txtName.text = puesto.puesto.name
		txtStatus.text = when(puesto.puesto.status) {
			Workstation.Status.Free -> getString(R.string.free)
			Workstation.Status.Occupied -> getString(R.string.occupied)
			Workstation.Status.Unavailable -> getString(R.string.unavailable)
		}

	}
}
