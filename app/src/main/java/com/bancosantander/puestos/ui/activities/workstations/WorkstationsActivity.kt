package com.bancosantander.puestos.ui.activities.workstations

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.View
import com.bancosantander.puestos.R
import com.bancosantander.puestos.ui.adapters.WorkstationsPageAdapter
import com.bancosantander.puestos.ui.presenters.WorkstationsPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity

/**
 * Created by bangulo on 18/12/2017.
 */
class WorkstationsActivity : BaseMvpActivity<WorkstationsViewContract.View,
        WorkstationsPresenter>(),
        MainViewContract.View {

    private var mWorkstationsPagerAdapter: WorkstationsPageAdapter? = null
    private var mViewPager: ViewPager? = null

    override var mPresenter: WorkstationsPresenter  = WorkstationsPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.workstations_activity)
        mPresenter.init()

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);

        val tabsList = arrayListOf(Fragment(),Fragment())

        mWorkstationsPagerAdapter = WorkstationsPageAdapter( supportFragmentManager,
                                                            this,
                                                                tabsList)

        mViewPager = findViewById(R.id.workstations_container)
        mViewPager!!.adapter = mWorkstationsPagerAdapter

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(mViewPager)

        tabLayout.getTabAt(0)!!.text = getString(R.string.workstations_list)
        tabLayout.getTabAt(1)!!.text = getString(R.string.workstations_map)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}