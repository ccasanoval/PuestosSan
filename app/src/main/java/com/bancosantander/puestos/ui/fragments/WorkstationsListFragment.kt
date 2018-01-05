package com.bancosantander.puestos.ui.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bancosantander.puestos.R
import com.bancosantander.puestos.data.models.Workstation
import com.bancosantander.puestos.ui.activities.ownWorkstation.OwnWorkstationActivity
import com.bancosantander.puestos.ui.activities.workstations.WorkstationsActivity
import com.bancosantander.puestos.ui.adapters.WorkstationsListAdapter
import com.bancosantander.puestos.ui.dialogs.CalendarViewDialog
import com.bancosantander.puestos.ui.dialogs.InfoDialog
import com.bancosantander.puestos.ui.dialogs.SiNoDialog
import com.bancosantander.puestos.ui.presenters.fragments.WorkstationsListFragmentPresenter
import com.bancosantander.puestos.ui.views.WorkstationsViewFragmentContract
import com.bancosantander.puestos.util.firebase
import com.bancosantander.puestos.util.presentation
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpFragment
import kotlinx.android.synthetic.main.workstations_activity.*
import kotlinx.android.synthetic.main.workstations_list_fragment.*
import org.jetbrains.anko.contentView
import java.util.*

/**
 * Created by bangulo on 19/12/2017.
 */
class WorkstationsListFragment : BaseMvpFragment<WorkstationsViewFragmentContract.View,WorkstationsListFragmentPresenter>(), WorkstationsViewFragmentContract.View, WorkstationsListAdapter.OnItemClickListener {

    var adapter : WorkstationsListAdapter?  = null
    var date : Date = Date()

    override lateinit var mPresenter: WorkstationsListFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter = WorkstationsListFragmentPresenter(activity as WorkstationsActivity)
        mPresenter.init()
        return inflater?.inflate(R.layout.workstations_list_fragment,container,false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        workstations_list.setHasFixedSize(true);
        val mLayoutManager = LinearLayoutManager(activity);
        workstations_list.layoutManager = mLayoutManager;
        adapter = WorkstationsListAdapter()
        mPresenter.setData()
        activity.tvDateSelected.text = date.presentation()
        workstations_list.adapter = adapter
        activity.ivCalendar.setOnClickListener{
            CalendarViewDialog.getInstance(date,callback = { date ->
                activity.tvDateSelected.text = date.presentation()
                mPresenter?.setData(date)
                this.date =date
            }).show(activity.supportFragmentManager, OwnWorkstationActivity.TAG_CALENDAR_DIALOG.name)}
    }

    override fun getMyActivity(): BaseMvpActivity<*,*> {
        return activity as WorkstationsActivity
    }


    override fun setDataAdapter(list: ArrayList<Workstation>) {

        adapter?.setDataAndListener(list,this) ?: Log.d("TAG","ADAPTER NULL")
    }
    override fun onItemClickListener(view: View, workstation: Workstation) {
        SiNoDialog.showSiNo(context,
                getString(R.string.fill_workstation),
                { si -> if(si) mPresenter.checkIfUserHaveWorkstation(workstation.idOwner,date.firebase())  })
    }

    override fun showLoading() {
        (activity as BaseMvpActivity<*,*>).showLoadingDialog()
    }

    override fun hideLoading() {
        (activity as BaseMvpActivity<*,*>).hideLoadingDialog()
    }

    override fun showDialog(title: Int) {
        InfoDialog.newInstance(context,getString(title))
    }
}