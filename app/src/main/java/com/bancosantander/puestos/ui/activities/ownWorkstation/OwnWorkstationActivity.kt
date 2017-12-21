package com.bancosantander.puestos.ui.activities.ownWorkstation

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
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
        setupToolbar()
        btnLiberar.setOnClickListener{ mPresenter.releaseMyWorkstation() }
    }

    private fun setupToolbar() {
            val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true);
            supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun showWorkstation(idOwner: String, idUser: String, status: Workstation.Status,number: String) {
        tvOwnerWorkstation.text = idOwner
        tvStateWorkstation.text = status.name
        tvNumberWorkstation.text = number
    }

    override fun finishActivity() {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
