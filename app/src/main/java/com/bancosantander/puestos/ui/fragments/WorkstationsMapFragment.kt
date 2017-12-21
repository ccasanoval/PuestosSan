package com.bancosantander.puestos.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.adapters.WorkstationsListAdapter
import com.bancosantander.puestos.ui.dialogs.SiNoDialog
import com.bancosantander.puestos.ui.presenters.WorkstationsPresenter
import com.bancosantander.puestos.ui.presenters.fragments.WorkstationsListFragmentPresenter
import com.bancosantander.puestos.ui.presenters.fragments.WorkstationsMapFragmentPresenter
import com.bancosantander.puestos.ui.views.MainViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewContract
import com.bancosantander.puestos.ui.views.WorkstationsViewFragmentContract
import com.bancosantander.puestos.ui.views.map.WorkstationsMapViewFragmentContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.workstations_list_fragment.*

/**
 * Created by bangulo on 19/12/2017.
 */
class WorkstationsMapFragment : BaseMvpFragment<WorkstationsMapViewFragmentContract.View, WorkstationsMapFragmentPresenter>(), WorkstationsMapViewFragmentContract.View {



    override lateinit var mPresenter: WorkstationsMapFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = WorkstationsMapFragmentPresenter(activity as WorkstationsActivity)
        mPresenter.init()
        return inflater?.inflate(R.layout.workstations_map_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getMyActivity(): AppCompatActivity {
        return activity as WorkstationsActivity
    }

}