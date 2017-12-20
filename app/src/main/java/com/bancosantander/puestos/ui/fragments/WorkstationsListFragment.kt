package com.bancosantander.puestos.ui.fragments

import android.os.Bundle
import android.support.design.widget.Snackbar
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

    var adapter : WorkstationsListAdapter = WorkstationsListAdapter()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.workstations_list_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val workstation = Workstation("aa", "sss", "fff", Workstation.Status.Free, 10F, 40F)
        val workstation2 = Workstation("aa2", "sss2", "fff2", Workstation.Status.Free, 10F, 40F)
        adapter.workstations =arrayListOf(workstation,workstation2)
        workstations_list.adapter = adapter
        val baseMvpActivity = activity as BaseMvpActivity<*, *>
        val workstationsPresenter = baseMvpActivity.mPresenter as WorkstationsPresenter
        workstationsPresenter.setData()
    }
    override fun setDataAdapter(list: ArrayList<Workstation>) {
       // adapter.setDataAndListener(list,this)
    }
    override fun onItemClickListener(view: View, workstation: Workstation) {
        Snackbar.make(getView()!!,"Click",Snackbar.LENGTH_LONG).show()
    }
}