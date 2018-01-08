package com.bancosantander.puestos.ui.activities.ownWorkstation

import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.view.menu.MenuBuilder
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.dialogs.CalendarViewDialog
import com.bancosantander.puestos.ui.presenters.OwnWorkstationPresenter
import com.bancosantander.puestos.ui.viewModels.ownWorkstation.OwnWorkstationViewModel
import com.bancosantander.puestos.ui.views.OwnWorkstationViewContract
import com.bancosantander.puestos.util.consume
import com.bancosantander.puestos.util.firebase
import com.bancosantander.puestos.util.presentation
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_own_workstation.*
import java.util.*


class OwnWorkstationActivity : BaseMvpActivity<OwnWorkstationViewContract.View,
        OwnWorkstationPresenter>(),
        OwnWorkstationViewContract.View {

    object TAG_CALENDAR_DIALOG {
        val name = "CalendarViewDialog"
    }

    var date: Date = Date()

    override var mPresenter: OwnWorkstationPresenter = OwnWorkstationPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_own_workstation)
        val model = ViewModelProviders.of(this).get(OwnWorkstationViewModel::class.java)
        mPresenter.init(model)
        setupToolbar()
        btnLiberar.setOnClickListener { mPresenter.releaseMyWorkstation(date = date.firebase()) }
        btnOcupar.setOnClickListener { mPresenter.fillWorkstation(date = date.firebase()) }

    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.menu_ownworkstation, menu)
        (menu as MenuBuilder).setOptionalIconsVisible(true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.multiple_free -> consume {
            date= Date()
            showCalendar(true)
        }
        R.id.select_day -> consume { showCalendar(false) }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showCalendar(multipleSelection: Boolean) {
        CalendarViewDialog.getInstance(multipleSelection, date, callback = { date ->
            when(multipleSelection){
                true -> consume {
                    //Reset values of own workstation to the actual date and free al the dates selected in the calendar
                    tvDateSelected.text = Date().presentation()
                    mPresenter?.showCurrentWorkstation(Date())
                    this.date = Date()
                    date?.forEach { mPresenter?.releaseMyWorkstation(it.firebase())}
                }
                false -> consume {
                    date?.get(0)?.let {
                        tvDateSelected.text = it.presentation()
                        mPresenter?.showCurrentWorkstation(it)
                        this.date =it
                    }
                }
            }
        }).show(supportFragmentManager, TAG_CALENDAR_DIALOG.name)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        tvDateSelected.text = Date().presentation()
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun showWorkstation(idOwner: String, idUser: String, status: Workstation.Status, number: String) {
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

    override fun hideButtons() {
        btnLiberar.visibility = View.GONE
        btnOcupar.visibility = View.GONE
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
