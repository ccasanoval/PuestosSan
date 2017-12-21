package com.bancosantander.puestos.ui.activities.ownWorkstation

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.transition.Visibility
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.presenters.OwnWorkstationPresenter
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_own_workstation.*
import android.content.DialogInterface
import android.support.v7.app.AlertDialog


class OwnWorkstationActivity : BaseMvpActivity<OwnWorkstationViewContract.View,
        OwnWorkstationPresenter>(),
        OwnWorkstationViewContract.View {


    override  var mPresenter: OwnWorkstationPresenter = OwnWorkstationPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_own_workstation)
        val model = ViewModelProviders.of(this).get(OwnWorkstationViewModel::class.java)
        mPresenter.init(model)
        setupToolbar()
        btnLiberar.setOnClickListener{ mPresenter.releaseMyWorkstation() }
        btnOcupar.setOnClickListener{mPresenter.fillWorkstation()}
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
        tvUserWorkstation.text = idUser
        tvNumberWorkstation.text = number
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showBtnLiberar() {
        btnOcupar.visibility = View.GONE
        btnLiberar.visibility = View.VISIBLE
    }

    override fun showBtnOcupar() {
        btnLiberar.visibility = View.GONE
        btnOcupar.visibility = View.VISIBLE
    }

    override fun showMyDialog(title: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(title))
                .setCancelable(false)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                    finish()
                })
        val alert = builder.create()
        alert.show()
    }

    override fun showLoading() {
        super.showLoadingDialog()
    }

    override fun hideLoading() {
        super.hideLoadingDialog()
    }
}
