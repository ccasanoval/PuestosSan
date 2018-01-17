package com.bancosantander.puestos.ui.activities.ownWorkstation

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.presenters.OwnWorkstationMapPresenter
import com.bancosantander.puestos.ui.viewModels.map.OwnWorkstationMapViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationMapViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity


/**
 * Created by ccasanova on 17/01/2018
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
class OwnWorkstationMapActivity : BaseMvpActivity<
		OwnWorkstationMapViewContract.View,
		OwnWorkstationMapPresenter>(),
		OwnWorkstationMapViewContract.View {

	override var mPresenter: OwnWorkstationMapPresenter = OwnWorkstationMapPresenter()

	//______________________________________________________________________________________________
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.act_own_workstation_map)
		val model = ViewModelProviders.of(this).get(OwnWorkstationMapViewModel::class.java)
		mPresenter.init(model)
		setupToolbar()
	}

	//______________________________________________________________________________________________
	private fun setupToolbar() {
		val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
		setSupportActionBar(toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)
	}

	//______________________________________________________________________________________________
	override fun onSupportNavigateUp(): Boolean {
		onBackPressed()
		return true
	}

	//______________________________________________________________________________________________
	override fun hideLoading() {
	}

	//______________________________________________________________________________________________
	override fun showLoading() {
	}



}
