package com.bancosantander.puestos.ui.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.adapters.WorkstationsListAdapter
import com.bancosantander.puestos.ui.presenters.WorkstationsPresenter
import com.bancosantander.puestos.ui.views.WorkstationsViewFragmentContract
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.workstations_list_fragment.*

/**
 * Created by bangulo on 19/12/2017.
 */
class WorkstationsListFragment : BaseMvpFragment<WorkstationsViewFragmentContract.View>(), WorkstationsViewFragmentContract.View, WorkstationsListAdapter.OnItemClickListener {

    var adapter : WorkstationsListAdapter?  = null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.workstations_list_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workstations_list.setHasFixedSize(true);
        val mLayoutManager = LinearLayoutManager(activity);
        workstations_list.layoutManager = mLayoutManager;
        adapter = WorkstationsListAdapter()
        setData()
        workstations_list.adapter = adapter

    }

    private fun setData() {
        val baseMvpActivity = activity as BaseMvpActivity<*, *>
        val workstationsPresenter = baseMvpActivity.mPresenter as WorkstationsPresenter
        workstationsPresenter.setData()
    }

    override fun setDataAdapter(list: ArrayList<Workstation>) {
        adapter?.setDataAndListener(list,this)
    }
    override fun onItemClickListener(view: View, workstation: Workstation) {
        Snackbar.make(getView()!!,"Click",Snackbar.LENGTH_LONG).show()
    }
}