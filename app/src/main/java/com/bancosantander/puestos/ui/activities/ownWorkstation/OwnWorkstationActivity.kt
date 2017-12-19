package com.bancosantander.puestos.ui.activities.ownWorkstation

import android.os.Bundle
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.presenters.OwnWorkstationPresenter
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_own_workstation.*

class OwnWorkstationActivity : BaseMvpActivity<OwnWorkstationViewContract.View,
        OwnWorkstationPresenter>(),
        OwnWorkstationViewContract.View {


    override  var mPresenter: OwnWorkstationPresenter = OwnWorkstationPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_own_workstation)
        mPresenter.init()
        btnLiberar.setOnClickListener{ mPresenter.releaseMyWorkstation() }
    }

    override fun showWorkstation(idOwner: String, idUser: String, status: Workstation.Status,number: String) {
        tvOwnerWorkstation.text = idOwner
        tvStateWorkstation.text = status.name
        tvNumberWorkstation.text = number
    }
}
