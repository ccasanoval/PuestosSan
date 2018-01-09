package com.bancosantander.puestos.ui.activities.workstations

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.adapters.WorkstationsPageAdapter
import com.bancosantander.puestos.ui.dialogs.CalendarViewDialog
import com.bancosantander.puestos.ui.fragments.WorkstationsMapFragment
import com.bancosantander.puestos.ui.fragments.WorkstationsListFragment
import com.bancosantander.puestos.ui.presenters.WorkstationsPresenter
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.bancosantander.puestos.util.presentation
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.workstations_activity.*
import java.util.*

/**
 * Created by bangulo on 18/12/2017.
 */
class WorkstationsActivity : BaseMvpActivity<WorkstationsViewContract.View,
        WorkstationsPresenter>(),
        WorkstationsViewContract.View {


    private var mWorkstationsPagerAdapter: WorkstationsPageAdapter? = null
    override var mPresenter: WorkstationsPresenter  = WorkstationsPresenter()
    var date : Date = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workstations_activity)
        mPresenter.init()
        setupToolbar()
        setupTabLayout()
        ivCalendar.setOnClickListener{
            CalendarViewDialog.getInstance(false,date, { date ->
                date?.get(0)?.let {
                    tvDateSelected.text = it.presentation()
                    val workstationsListFragment = supportFragmentManager.fragments[0] as WorkstationsListFragment
                    workstationsListFragment.mPresenter.setData(it)
                    val workstationsMapFragment = supportFragmentManager.fragments[1] as WorkstationsMapFragment
                    workstationsMapFragment.mPresenter.setData(it)
                    this.date =it
                }
            }).show(supportFragmentManager, OwnWorkstationActivity.TAG_CALENDAR_DIALOG.name)}
    }

    private lateinit var tabsList: ArrayList<BaseMvpFragment<*,*>>

    private fun setupTabLayout() {
        //TODO realizar un new instance para los fragments
        tabsList = arrayListOf(WorkstationsListFragment(), WorkstationsMapFragment())

        mWorkstationsPagerAdapter = WorkstationsPageAdapter(supportFragmentManager,
                this,
                tabsList)
        workstations_container!!.adapter = mWorkstationsPagerAdapter


        tabs.setupWithViewPager(workstations_container)

        tabs.getTabAt(0)!!.setText(R.string.workstations_list)
        tabs.getTabAt(1)!!.setText(R.string.workstations_map)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}