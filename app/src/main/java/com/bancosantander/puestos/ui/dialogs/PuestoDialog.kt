package com.bancosantander.puestos.ui.dialogs

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.util.WorkstationParcelable
import kotlinx.android.synthetic.main.dlg_puesto.*

import android.arch.lifecycle.Observer
import com.bancosantander.puestos.ui.viewModels.PuestoViewModel
import com.bancosantander.puestos.util.Log


////////////////////////////////////////////////////////////////////////////////////////////////////
class PuestoDialog : AppCompatActivity() {
	private lateinit var viewModel : PuestoViewModel

	//______________________________________________________________________________________________
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.dlg_puesto)

		iniViewModel()
		iniParcel()
		iniVentana()
		iniBotones()
	}

	//______________________________________________________________________________________________
	private fun iniViewModel() {
		viewModel = ViewModelProviders.of(this).get(PuestoViewModel::class.java)
		viewModel.mensaje.observe(this, Observer { mensaje ->
			Toast.makeText(applicationContext, mensaje, Toast.LENGTH_LONG).show()
		})

	}
	//______________________________________________________________________________________________
	private fun iniParcel() {
		val parcel: WorkstationParcelable? = intent.extras.getParcelable(Workstation::class.java.name)
		Log.e(TAG, "iniParcel:---------------------------------------------"+parcel?.puesto)
		if(parcel != null)viewModel.puesto = parcel.puesto
		setCampos()
	}

	//______________________________________________________________________________________________
	private fun iniVentana(){
		val metrics = DisplayMetrics()
		windowManager.defaultDisplay.getMetrics(metrics)
		val params = window.attributes
		params.x = 0
		params.y = 0
		params.height = metrics.heightPixels-75 //1600
		params.width =  metrics.widthPixels-50//1000
		this.window.attributes = params
	}
	//______________________________________________________________________________________________
	private fun iniBotones() {
		btnCerrar.setOnClickListener { salir() }
	}

	//______________________________________________________________________________________________
	private fun setCampos() {
		if(viewModel.puesto != null) {
			//txtId.text = viewModel.puesto!!.id
			txtOwner.text = (viewModel.puesto!!.idOwner)
			txtUser.text = (viewModel.puesto!!.idUser)
			txtName.setText(viewModel.puesto!!.name)
			txtStatus.text = when(viewModel.puesto!!.status) {
				Workstation.Status.Free -> getString(R.string.free)
				Workstation.Status.Occupied -> getString(R.string.occupied)
				Workstation.Status.Unavailable -> getString(R.string.unavailable)
			}
		}
		//txtName.focusable = View.NOT_FOCUSABLE
		txtName.isEnabled = false
	}

	//______________________________________________________________________________________________
	private fun salir() {
		finish()
	}

	//______________________________________________________________________________________________
	companion object {
		private val TAG: String = PuestoDialog::class.java.simpleName
	}
}
