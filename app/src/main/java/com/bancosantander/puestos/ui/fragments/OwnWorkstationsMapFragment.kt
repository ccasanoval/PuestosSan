package com.bancosantander.puestos.ui.fragments

import android.content.Intent
import android.graphics.PointF
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.CommonArea
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationMapActivity
import com.bancosantander.puestos.ui.dialogs.InfoDialog
import com.bancosantander.puestos.ui.dialogs.PuestoDialog
import com.bancosantander.puestos.ui.presenters.fragments.OwnWorkstationsMapFragmentPresenter
import com.bancosantander.puestos.ui.views.map.OwnWorkstationsMapViewFragmentContract
import com.bancosantander.puestos.util.Log
import com.bancosantander.puestos.util.WorkstationParcelable
import com.davemorrissey.labs.subscaleview.ImageSource
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.act_main.*


////////////////////////////////////////////////////////////////////////////////////////////////////
class OwnWorkstationsMapFragment : BaseMvpFragment<OwnWorkstationsMapViewFragmentContract.View, OwnWorkstationsMapFragmentPresenter>(), OwnWorkstationsMapViewFragmentContract.View {

	override lateinit var mPresenter: OwnWorkstationsMapFragmentPresenter
	private var imgListener: View.OnTouchListener ?=null

	override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		mPresenter = OwnWorkstationsMapFragmentPresenter(activity as OwnWorkstationMapActivity)
		return inflater!!.inflate(R.layout.act_main, container, false)
	}

	//______________________________________________________________________________________________
	override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mPresenter.init()
		imgPlano.setImage(ImageSource.asset("plano.jpg"))
		mPresenter.setData()
		val gesture = getGestureDetector()
		imgListener = View.OnTouchListener { _, motionEvent ->
			gesture.onTouchEvent(motionEvent)
		}
		imgPlano.setOnTouchListener(imgListener)

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
		mPresenter.onStart()
	}

	//______________________________________________________________________________________________
	override fun onStop() {
		mPresenter.onStop()
		super.onStop()
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

	//______________________________________________________________________________________________
	private fun singleTapConfirmed(me: MotionEvent) {
		val pto = imgPlano.viewToSourceCoord(me.x, me.y)
		val pto100 = imgPlano.coordImgTo100(pto)
		mPresenter.setPoint(pto,pto100)
	}

	override fun showCamino(camino: Array<PointF>) {
		imgPlano.setCamino(camino)
	}

	override fun delCamino() {
		imgPlano.delCamino()
	}

	override fun showPointF(initial:Boolean, pto: PointF?){
		imgPlano.setPoint(initial,pto)
	}

	override fun showPuestos(puestos: List<Workstation>) {
		imgPlano.setPuestos(puestos)
	}

	override fun showCommons(commons: List<CommonArea>) {
		imgPlano.setCommons(commons)
	}

	override fun showSeleccionado(puesto: Workstation?) {
		imgPlano.setSeleccionado(puesto)
		if(puesto != null) {
			val intent = Intent(activity, PuestoDialog::class.java)
			intent.putExtra(Workstation::class.java.name, WorkstationParcelable(puesto))
			startActivity(intent)
		}
	}

	//______________________________________________________________________________________________
	override fun getMyActivity(): BaseMvpActivity<*,*> {
		return activity as OwnWorkstationMapActivity
	}

	override fun showLoading() {
		(activity as BaseMvpActivity<*,*>).showLoadingDialog()
	}

	override fun hideLoading() {
		(activity as BaseMvpActivity<*,*>).hideLoadingDialog()
	}


	override fun showDialog(title: Int) {
		InfoDialog.newInstance(context,getString(title))
	}


	//______________________________________________________________________________________________
	companion object {
		private val TAG: String = OwnWorkstationsMapFragment::class.java.simpleName
	}
}