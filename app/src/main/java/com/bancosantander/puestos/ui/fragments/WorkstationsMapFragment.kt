package com.bancosantander.puestos.ui.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.dialogs.CalendarViewDialog
import com.bancosantander.puestos.ui.dialogs.PuestoDialog
import com.bancosantander.puestos.ui.presenters.fragments.WorkstationsMapFragmentPresenter
import com.bancosantander.puestos.ui.viewModels.map.MapaViewModel
import com.bancosantander.puestos.ui.views.map.WorkstationsMapViewFragmentContract
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.WorkstationParcelable
import com.bancosantander.puestos.util.presentation
import com.davemorrissey.labs.subscaleview.ImageSource
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.act_main.*
import kotlinx.android.synthetic.main.workstations_activity.*
import kotlinx.android.synthetic.main.workstations_list_fragment.*
import java.util.*

////////////////////////////////////////////////////////////////////////////////////////////////////
class WorkstationsMapFragment : BaseMvpFragment<WorkstationsMapViewFragmentContract.View, WorkstationsMapFragmentPresenter>(), WorkstationsMapViewFragmentContract.View {

	override lateinit var mPresenter: WorkstationsMapFragmentPresenter
	private lateinit var viewModel : MapaViewModel
	private var imgListener: View.OnTouchListener ?=null

	//______________________________________________________________________________________________
	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		mPresenter = WorkstationsMapFragmentPresenter()
		return inflater!!.inflate(R.layout.act_main, container, false)
	}

	//______________________________________________________________________________________________
	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//Log.e(TAG, "tag-----------------------------------------------------------------------"+tag)
		imgPlano.setImage(ImageSource.asset("plano.jpg"))
		val gesture = getGestureDetector()
		imgListener = View.OnTouchListener { _, motionEvent ->
			gesture.onTouchEvent(motionEvent)
		}
		imgPlano.setOnTouchListener(imgListener)

		iniViewModel(view!!)

		activity.tvDateSelected.text = viewModel.fecha.presentation()
		activity.ivCalendar.setOnClickListener{
			CalendarViewDialog.getInstance(viewModel.fecha, callback = { date ->
				activity.tvDateSelected.text = date.presentation()
				viewModel.fecha = date
				viewModel.getPuestosRT()
			}).show(activity.supportFragmentManager, OwnWorkstationActivity.TAG_CALENDAR_DIALOG.name)}
	}

	//______________________________________________________________________________________________
	override fun onDestroy() {
		super.onDestroy()
		if(imgPlano != null) {
			imgPlano.destroyDrawingCache()
			imgPlano.recycle()
		}
	}

	//______________________________________________________________________________________________
	override fun onStart() {
		super.onStart()
		viewModel.onStart()
	}

	//______________________________________________________________________________________________
	override fun onStop() {
		viewModel.onStop()
		super.onStop()
	}

	//______________________________________________________________________________________________
	private fun iniViewModel(view: View) {
		viewModel = ViewModelProviders.of(this@WorkstationsMapFragment).get(MapaViewModel::class.java)
		viewModel.mensaje.observe(this, Observer { mensaje ->
//			Toast.makeText(activity, mensaje, Toast.LENGTH_LONG).show()
		})
		viewModel.camino.observe(this, Observer<Array<PointF>> { camino ->
			if(camino == null)	delCamino()
			else				showCamino(camino)
		})
		viewModel.puestos.observe(this, Observer<List<Workstation>> { puestos ->
			//Log.e(TAG, "------------------------------- PUESTOS OBSERVER")
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

	//______________________________________________________________________________________________
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

	//______________________________________________________________________________________________
	private fun singleTapConfirmed(me: MotionEvent) {
		val pto = imgPlano.viewToSourceCoord(me.x, me.y)
		val pto100 = imgPlano.coordImgTo100(pto)
		viewModel.punto(pto, pto100)
	}

	//______________________________________________________________________________________________
	private fun showCamino(camino: Array<PointF>) {
		imgPlano.setCamino(camino)
	}

	//______________________________________________________________________________________________
	private fun delCamino() {
		imgPlano.delCamino()
	}

	//______________________________________________________________________________________________
	private fun showPointF(initial:Boolean, pto: PointF?){
		imgPlano.setPoint(initial,pto)
	}

	//______________________________________________________________________________________________
	private fun showPuestos(puestos: List<Workstation>) {
		Log.e(TAG, "showPuestos--------------------------------")
		imgPlano.setPuestos(puestos)
	}

	//______________________________________________________________________________________________
	private fun showSeleccionado(puesto: Workstation?) {
		imgPlano.setSeleccionado(puesto)
		if(puesto != null) {
			val intent = Intent(activity, PuestoDialog::class.java)
			intent.putExtra(Workstation::class.java.name, WorkstationParcelable(puesto))
			startActivity(intent)
		}
	}

	//______________________________________________________________________________________________
	override fun getMyActivity(): BaseMvpActivity<*,*> {
		return activity as WorkstationsActivity
	}


	//______________________________________________________________________________________________
	companion object {
		private val TAG: String = WorkstationsMapFragment::class.java.simpleName
	}
}