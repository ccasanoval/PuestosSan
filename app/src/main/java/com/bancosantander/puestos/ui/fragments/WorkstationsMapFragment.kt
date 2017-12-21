package com.bancosantander.puestos.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.dialogs.PuestoDialog
import com.bancosantander.puestos.ui.presenters.fragments.WorkstationsMapFragmentPresenter
import com.bancosantander.puestos.ui.viewModels.map.MapaViewModel
import com.bancosantander.puestos.ui.views.map.WorkstationsMapViewFragmentContract
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.WorkstationParcelable
import com.davemorrissey.labs.subscaleview.ImageSource
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.act_main.*


class WorkstationsMapFragment : BaseMvpFragment<WorkstationsMapViewFragmentContract.View, WorkstationsMapFragmentPresenter>(), WorkstationsMapViewFragmentContract.View {

	override lateinit var mPresenter: WorkstationsMapFragmentPresenter
	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		mPresenter = WorkstationsMapFragmentPresenter()
		return inflater!!.inflate(R.layout.act_main, container, false)
	}

	companion object {
		private val TAG: String = WorkstationsMapFragment::class.java.simpleName
	}


	private lateinit var viewModel : MapaViewModel
	private var imgListener: View.OnTouchListener ?=null

	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		imgPlano.setImage(ImageSource.asset("plano.jpg"))
		val gesture = getGestureDetector()
		imgListener = View.OnTouchListener { _, motionEvent ->
			gesture.onTouchEvent(motionEvent)
		}
		imgPlano.setOnTouchListener(imgListener)

		iniViewModel(view!!)
	}

	override fun onDestroy() {

		super.onDestroy()
		if(imgPlano != null) {
			imgPlano.destroyDrawingCache()
			imgPlano.recycle()
		}
	}

	private fun iniViewModel(view: View) {
		viewModel = ViewModelProviders.of(this@WorkstationsMapFragment).get(MapaViewModel::class.java)
		viewModel.mensaje.observe(this, Observer { mensaje ->
			Toast.makeText(activity, mensaje, Toast.LENGTH_LONG).show()
		})
		viewModel.camino.observe(this, Observer<Array<PointF>> { camino ->
			if(camino == null)	delCamino()
			else				showCamino(camino)
		})
		viewModel.puestos.observe(this, Observer<List<Workstation>> { puestos ->
			Log.e(TAG, "------------------------------- PUESTOS OBSERVER")
			when {
				puestos == null ->
					Toast.makeText(activity, getString(R.string.puestos_get_none), Toast.LENGTH_SHORT).show()
					//Toast.makeText(activity, getString(R.string.puestos_get_error), Toast.LENGTH_SHORT).show()
				puestos.isEmpty() ->
					Toast.makeText(activity, getString(R.string.puestos_get_none), Toast.LENGTH_SHORT).show()
				else ->
					showPuestos(puestos)
			}
		})
		viewModel.selected.observe(this, Observer<Workstation>{ pto -> showSeleccionado(pto) })
		viewModel.ini.observe(this, Observer<PointF>{ pto -> showPointF(true,pto) })
		viewModel.end.observe(this, Observer<PointF>{ pto -> showPointF(false,pto) })
	}

	private fun getGestureDetector() =
		GestureDetector(activity, object : GestureDetector.SimpleOnGestureListener() {
			override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
				if(imgPlano.isReady)
					singleTapConfirmed(e)
				else
					Toast.makeText(activity, getString(R.string.cargando_imagen), Toast.LENGTH_SHORT).show()
				return false
			}
		})

	private fun singleTapConfirmed(me: MotionEvent) {
		val pto = imgPlano.viewToSourceCoord(me.x, me.y)
		val pto100 = imgPlano.coordImgTo100(pto)
		viewModel.punto(pto, pto100)
	}


	private fun showCamino(camino: Array<PointF>) {
		imgPlano.setCamino(camino)
	}
	private fun delCamino() {
		imgPlano.delCamino()
	}

	private fun showPointF(initial:Boolean, pto: PointF?){
		imgPlano.setPoint(initial,pto)
	}

	private fun showPuestos(puestos: List<Workstation>) {
		Log.e(TAG, "showPuestos--------------------------------")
		imgPlano.setPuestos(puestos)
	}

	private fun showSeleccionado(puesto: Workstation?) {
		imgPlano.setSeleccionado(puesto)
		if(puesto != null) {
			val intent = Intent(activity, PuestoDialog::class.java)
			intent.putExtra(Workstation::class.java.name, WorkstationParcelable(puesto))
			startActivity(intent)
		}
	}
	override fun getMyActivity(): AppCompatActivity {
		return activity as WorkstationsActivity
	}

}