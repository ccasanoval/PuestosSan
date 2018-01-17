package com.bancosantander.puestos.ui.views.map

import android.graphics.PointF
import com.bancosantander.puestos.data.models.CommonArea
import com.bancosantander.puestos.data.models.Workstation
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpPresenter
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpView

////////////////////////////////////////////////////////////////////////////////////////////////////
object WorkstationsMapViewFragmentContract {

    interface View: BaseMvpView {
        fun showLoading()
        fun hideLoading()
        fun showDialog(title: Int)
        fun delCamino()
        fun showCamino(camino: Array<PointF>)
        fun showPuestos(puestos: List<Workstation>)
		fun showCommons(commons: List<CommonArea>)
        fun showSeleccionado(puesto: Workstation?)
        fun showPointF(initial: Boolean, pto: PointF?)
    }
    interface Presenter : BaseMvpPresenter<View> {
        fun init()
        fun onStop()
        fun onStart()
        fun setPoint(pto: PointF,pto100: PointF)
    }
}